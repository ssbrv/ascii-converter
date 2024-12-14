package common.mapper

import parser.domain.{AssetHandler, Command}

abstract class AssetMapper[T] extends Mapper[Command, T] {
  override def map(command: Command): Option[T] = {
    val optionalAssetHandler = getAssetHandler(command.name)

    if (optionalAssetHandler.isEmpty)
      return None

    val assetHandler = optionalAssetHandler.get

    val paramCount = command.parameters.length
    val allowedParamCount = assetHandler.allowedParamCount
    if (allowedParamCount.contains(paramCount)
      || AssetHandler.isAnyCountAllowed(allowedParamCount)
      || (AssetHandler.isFromMinToAnyAllowed(allowedParamCount) && paramCount >= AssetHandler.getMin(allowedParamCount).get))
      return Some(assetHandler.createCallback.apply(command.parameters))

    val params = command.parameters.mkString("[", ", ", "]")

    val expectedRange: String = if (AssetHandler.isFromMinToAnyAllowed(allowedParamCount))
      s"${AssetHandler.getMin(allowedParamCount).get} - ..."
    else allowedParamCount.mkString(" / ")

    throw new IllegalArgumentException(
      s"Parameter count mismatch for command '${command.name}'. " +
        s"Expected $expectedRange parameters, but got $paramCount. " +
        s"Provided parameters: $params."
    )
  }

  def createDefault: Option[T] = None
  protected def getAssetHandler(commandName: String): Option[AssetHandler[T]]
}
