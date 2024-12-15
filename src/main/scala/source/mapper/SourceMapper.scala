package source.mapper

import common.mapper.AssetMapper
import parser.domain.AssetHandler
import source.mapper.importer.FileImporterMapper
import source.service.MediaSource
import source.service.random.{RandomImageGenerator, SmallImageGenerator}

class SourceMapper(fileImporterMapper: FileImporterMapper) extends AssetMapper[MediaSource] {
  private val sourceHandlers: Map[String, AssetHandler[MediaSource]] = Map(
    "image" -> AssetHandler[MediaSource](params => fileImporterMapper.map(params.last), Set(1)),
    "image-random" -> AssetHandler[MediaSource](params => createGenerator(params), Set(0, 2)),
  )

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

  override protected def getAssetHandler(commandName: String): Option[AssetHandler[MediaSource]] = sourceHandlers.get(commandName)
  override def createDefault: Option[MediaSource] = None // Some(new SmallImageGenerator)
}
