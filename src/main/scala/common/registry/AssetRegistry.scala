package common.registry

import parser.domain.AssetHandler

abstract class AssetRegistry[T] {
  def getAssetHandler(commandName: String): Option[AssetHandler[T]]
  def createDefault: Option[T] = None
}
