package filter.registry

import common.registry.AssetRegistry
import filter.service.ascii.{AsciiFilter, FontAspectRationFilter}
import parser.domain.{AssetHandler, ParameterCountSingle}

class AsciiFilterRegistry extends AssetRegistry[AsciiFilter] {
  private val filterHandlers: Map[String, AssetHandler[AsciiFilter]] = Map(
    "font-aspect-ratio" -> AssetHandler[AsciiFilter](params => createFontAspectRationFilter(params), new ParameterCountSingle(1)),
  )

  private def createFontAspectRationFilter(params: Seq[String]): FontAspectRationFilter = {
    val xy = params.head.split(":")
    require(xy.length == 2, "Font aspect ratio must be in the format 'x:y'.")

    val x = try {
      xy(0).toInt
    } catch {
      case e: NumberFormatException =>
        throw new NumberFormatException(s"x in 'x:y' (font aspect ratio) is not a number: ${xy(0)}. Number is expected.")
    }

    val y = try {
      xy(1).toInt
    } catch {
      case e: NumberFormatException =>
        throw new NumberFormatException(s"y in 'x:y' (font aspect ratio) is not a number: ${xy(1)}. Number is expected.")
    }

    require(x > 0 && y > 0, "Font aspect ratio values must be positive integers.")

    new FontAspectRationFilter(x, y)
  }

  override def getAssetHandler(commandName: String): Option[AssetHandler[AsciiFilter]] = filterHandlers.get(commandName)
}
