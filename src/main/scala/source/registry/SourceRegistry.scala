package source.registry

import common.registry.AssetRegistry
import common.utils.file.FileManager
import parser.domain.{AssetHandler, ParameterCountSet, ParameterCountSingle}
import source.service.MediaSource
import source.service.importer.{FileImporter, MultiFramerImporter, ImageImporter}
import source.service.random.{RandomImageGenerator, SmallImageGenerator}

import java.io.File

class SourceRegistry(private val fileManager: FileManager) extends AssetRegistry[MediaSource] {
  private val sourceHandlers: Map[String, AssetHandler[MediaSource]] = Map(
    "image" -> AssetHandler[MediaSource](createImporter, new ParameterCountSingle(1)),
    "image-random" -> AssetHandler[MediaSource](createGenerator, new ParameterCountSet(Set(0, 2))),
  )

  private val definedFormatsForImport: Map[String, File => FileImporter] = Map(
    "jpeg" -> createImageImporter,
    "jpg" -> createImageImporter,
    "png" -> createImageImporter,
    "gif" -> createMultiFramerImporter
  )
  private val allowedFileTypes = definedFormatsForImport.map((fileType, _) => fileType).toSeq
  
  private def createImporter(params: Seq[String]): FileImporter = {
    val filePath = params.last

    val file = new File(filePath)

    if (!fileManager.exists(file))
      throw new IllegalArgumentException(s"File does not exist: $filePath")

    if (!fileManager.isFile(file))
      throw new IllegalArgumentException(s"$filePath is not a file")

    if (!fileManager.canRead(file))
      throw new IllegalArgumentException(s"Cannot read from file: $filePath")

    val fileType = fileManager.detectFileType(file)
      .getOrElse(throw new IllegalArgumentException(s"Could not determine file type: $filePath"))

    definedFormatsForImport.getOrElse(fileType, throw new IllegalArgumentException(
      s"Provided unsupported file type: $file. " +
        s"Allowed: ${allowedFileTypes.mkString(", ")}"
    )).apply(file)
  }

  private def createGenerator(params: Seq[String]): RandomImageGenerator = {
    if (params.isEmpty)
      return new SmallImageGenerator

    val width = try {
      params.head.toInt
    } catch {
    case e: NumberFormatException =>
      throw new NumberFormatException(s"First parameter of generate command is not a number: ${params.head}. Number is expected.")
    }

    require(width > 0, s"First parameter of generate command is not a positive number: $width. Positive number is expected.")

    val height = try {
      params(1).toInt
    } catch {
      case e: NumberFormatException =>
        throw new NumberFormatException(s"Second parameter of generate command is not a number: ${params(1)}. Number is expected.")
    }
    
    require(height > 0, s"Second parameter of generate command is not a positive number: $height. Positive number is expected.")

    new RandomImageGenerator(width, width, height, height)
  }

  private def createMultiFramerImporter(file: File): MultiFramerImporter = new MultiFramerImporter(file)
  private def createImageImporter(file: File): ImageImporter = new ImageImporter(file)

  override def getAssetHandler(commandName: String): Option[AssetHandler[MediaSource]] = sourceHandlers.get(commandName)
  override def createDefault: Option[MediaSource] = None // Some(new SmallImageGenerator)
}
