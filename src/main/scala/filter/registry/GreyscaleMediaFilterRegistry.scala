package filter.registry

import common.registry.AssetRegistry
import filter.service.greyscale.{BrightnessFilter, GreyscaleMediaFilter, InvertFilter}
import parser.domain.{AssetHandler, ParameterCountSingle}

class GreyscaleMediaFilterRegistry extends AssetRegistry[GreyscaleMediaFilter] {
  private val filterHandlers: Map[String, AssetHandler[GreyscaleMediaFilter]] = Map(
    "brightness" -> AssetHandler[GreyscaleMediaFilter](createBrightnessFilter, new ParameterCountSingle(1)),
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

  override def getAssetHandler(commandName: String): Option[AssetHandler[GreyscaleMediaFilter]] = filterHandlers.get(commandName)
}
