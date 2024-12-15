package filter.mapper

import common.mapper.AssetMapper
import filter.service.greyscale.{BrightnessFilter, GreyscaleMediaFilter, InvertFilter}
import parser.domain.AssetHandler

class GreyscaleMediaFilterMapper extends AssetMapper[GreyscaleMediaFilter] {
  private val filterHandlers: Map[String, AssetHandler[GreyscaleMediaFilter]] = Map(
    "rotate" -> AssetHandler[GreyscaleMediaFilter](params => null, Set(1, 2)),
    "brightness" -> AssetHandler[GreyscaleMediaFilter](createBrightnessFilter, Set(1)),
    "invert" -> AssetHandler[GreyscaleMediaFilter](_ => new InvertFilter),
  )

  private def createBrightnessFilter(params: Seq[String]): BrightnessFilter = {
    val brightnessDelta = try {
      params.head.toInt
    } catch {
      case e: NumberFormatException =>
        throw new NumberFormatException(s"Brightness delta is not a number: ${params.head}. Number is expected.")
    }

    new BrightnessFilter(brightnessDelta)
  }

  override protected def getAssetHandler(commandName: String): Option[AssetHandler[GreyscaleMediaFilter]] = filterHandlers.get(commandName)
}
