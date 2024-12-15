package filter.mapper

import common.mapper.AssetMapper
import filter.service.ascii.{AsciiFilter, FontAspectRationFilter}
import parser.domain.AssetHandler

class AsciiFilterMapper extends AssetMapper[AsciiFilter] {
  private val filterHandlers: Map[String, AssetHandler[AsciiFilter]] = Map(
    "rotate" -> AssetHandler[AsciiFilter](params => null, Set(1, 2)),
    "scale" -> AssetHandler[AsciiFilter](params => null, Set(1, 2)),
    "font-aspect-ratio" -> AssetHandler[AsciiFilter](params => createFontAspectRationFilter(params), Set(1)),
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

  override protected def getAssetHandler(commandName: String): Option[AssetHandler[AsciiFilter]] = filterHandlers.get(commandName)
}
