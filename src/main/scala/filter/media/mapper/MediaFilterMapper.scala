package filter.media.mapper

import common.mapper.AssetMapper
import filter.media.service.MediaFilter
import parser.domain.AssetHandler

class MediaFilterMapper extends AssetMapper[MediaFilter] {
  private val filterHandlers: Map[String, AssetHandler[MediaFilter]] = Map(
    "rotate" -> AssetHandler[MediaFilter](params => null, Set(1, 2)),
    "scale" -> AssetHandler[MediaFilter](params => null, Set(1, 2)),
    "invert" -> AssetHandler[MediaFilter](params => null, Set(1, 2)),
  )

  override protected def getAssetHandler(commandName: String): Option[AssetHandler[MediaFilter]] = filterHandlers.get(commandName)
}
