package source.registry

import common.registry.AssetRegistry
import parser.domain.{AssetHandler, ParameterCountSet, ParameterCountSingle}
import source.mapper.FileImporterMapper
import source.service.MediaSource
import source.service.importer.FileImporter
import source.service.random.{RandomImageGenerator, SmallImageGenerator}

class SourceRegistry(fileImporterMapper: FileImporterMapper) extends AssetRegistry[MediaSource] {
  private val sourceHandlers: Map[String, AssetHandler[MediaSource]] = Map(
    "image" -> AssetHandler[MediaSource](createImporter, new ParameterCountSingle(1)),
    "image-random" -> AssetHandler[MediaSource](createGenerator, new ParameterCountSet(Set(0, 2))),
  )
  
  private def createImporter(params: Seq[String]): FileImporter = {
    fileImporterMapper.map(params.last).getOrElse(
      throw new IllegalArgumentException(
        s"Provided unsupported file type: ${fileImporterMapper.detectFileType(params.last)}. " + 
          s"Allowed: ${fileImporterMapper.getAllowedFileTypes.mkString(", ")}"
      )
    )
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

  override def getAssetHandler(commandName: String): Option[AssetHandler[MediaSource]] = sourceHandlers.get(commandName)
  override def createDefault: Option[MediaSource] = None // Some(new SmallImageGenerator)
}
