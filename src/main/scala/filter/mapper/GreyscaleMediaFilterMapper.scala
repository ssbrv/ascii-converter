package filter.mapper

import common.mapper.AssetMapper
import filter.service.greyscale.{GreyscaleMediaFilter, InvertFilter}
import parser.domain.AssetHandler

class GreyscaleMediaFilterMapper extends AssetMapper[GreyscaleMediaFilter] {
  private val filterHandlers: Map[String, AssetHandler[GreyscaleMediaFilter]] = Map(
    "rotate" -> AssetHandler[GreyscaleMediaFilter](params => null, Set(1, 2)),
    "scale" -> AssetHandler[GreyscaleMediaFilter](params => null, Set(1, 2)),
    "invert" -> AssetHandler[GreyscaleMediaFilter](_ => new InvertFilter),
  )

  override protected def getAssetHandler(commandName: String): Option[AssetHandler[GreyscaleMediaFilter]] = filterHandlers.get(commandName)
}
