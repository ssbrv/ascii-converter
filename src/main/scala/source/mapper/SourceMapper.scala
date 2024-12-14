package source.mapper

import common.mapper.AssetMapper
import parser.domain.AssetHandler
import source.service.MediaSource
import source.service.importer.{ImageImporter, MultiFramerImporter}
import source.service.randomgenerator.{RandomImageGenerator, SmallImageGenerator}

import java.io.File
import java.nio.file.Files

class SourceMapper extends AssetMapper[MediaSource] {
  private val sourceHandlers: Map[String, AssetHandler[MediaSource]] = Map(
    "image" -> AssetHandler[MediaSource](params => createImporter(params.last), Set(1)),
    "image-random" -> AssetHandler[MediaSource](params => createGenerator(params), Set(0, 2)),
  )

  private val definedFormatsForImport: Map[String, File => MediaSource] = Map(
    "image/jpeg" -> createImageImporter,
    "image/png" -> createImageImporter,
    "image/gif" -> createMultiFramerImporter
  )

  private def createImporter(filePath: String): MediaSource = {
    val file = new File(filePath)
    if (!file.exists())
      throw new IllegalArgumentException(s"File does not exist: $filePath")

    if (!file.isFile)
      throw new IllegalArgumentException(s"$filePath is not a file")

    if (!file.canRead)
      throw new IllegalArgumentException(s"Cannot read from file: $filePath")

    val mimeType = detectMimeType(file)
      .getOrElse(throw new IllegalArgumentException(s"Could not determine file type: $filePath"))
    
    definedFormatsForImport
      .getOrElse(
        mimeType,
        throw new IllegalArgumentException(s"Unsupported file type: $mimeType")
      )(file)
  }

  private def createGenerator(params: Seq[String]): RandomImageGenerator = {
    if (params.isEmpty)
      return new SmallImageGenerator

    val from = try {
      params.head.toInt
    } catch {
    case e: NumberFormatException =>
      throw new NumberFormatException(s"First parameter of generate command is not a number: ${params.head}. Number is expected.")
    }

    val to = try {
      params(1).toInt
    } catch {
      case e: NumberFormatException =>
        throw new NumberFormatException(s"Second parameter of generate command is not a number: ${params(1)}. Number is expected.")
    }

    new RandomImageGenerator(from, from, to, to)
  }

  private def detectMimeType(file: File): Option[String] = {
    try {
      Option(Files.probeContentType(file.toPath))
    } catch {
      case _: Exception => None
    }
  }

  private def createMultiFramerImporter(file: File): MultiFramerImporter = new MultiFramerImporter(file)
  private def createImageImporter(file: File): ImageImporter = new ImageImporter(file)

  override protected def getAssetHandler(commandName: String): Option[AssetHandler[MediaSource]] = sourceHandlers.get(commandName)
  override def createDefault: Option[MediaSource] = None // Some(new SmallImageGenerator)
}
