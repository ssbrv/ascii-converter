package filter.ascii.mapper

import common.mapper.AssetMapper
import filter.ascii.service.AsciiFilter
import parser.domain.AssetHandler

class AsciiFilterMapper extends AssetMapper[AsciiFilter] {
  private val filterHandlers: Map[String, AssetHandler[AsciiFilter]] = Map(
    "rotate" -> AssetHandler[AsciiFilter](params => null, Set(1, 2)),
    "scale" -> AssetHandler[AsciiFilter](params => null, Set(1, 2)),
    "invert" -> AssetHandler[AsciiFilter](params => null, Set(1, 2)),
  )

  override protected def getAssetHandler(commandName: String): Option[AssetHandler[AsciiFilter]] = filterHandlers.get(commandName)
}
