package common.mapper

import common.registry.AssetRegistry
import parser.domain.Command

class AssetMapperImpl[T](assetRegistry: AssetRegistry[T]) extends AssetMapper[T] {
  override def map(command: Command): Option[T] = {
    val optionalAssetHandler = assetRegistry.getAssetHandler(command.name)

    if (optionalAssetHandler.isEmpty)
      return None

    val assetHandler = optionalAssetHandler.get

    val paramCount = command.parameters.length
    val allowedParamCount = assetHandler.allowedParamCount
    if (allowedParamCount.isAllowed(paramCount))
      return Some(assetHandler.createCallback.apply(command.parameters))

    val params = command.parameters.mkString("[", ", ", "]")
    val expectedParamCount = allowedParamCount.getWhatIsAllowed

    throw new IllegalArgumentException(
      s"Parameter count mismatch for command '${command.name}'. " +
        s"Expected $expectedParamCount parameter(s), but got $paramCount. " +
        s"Provided parameter(s): $params."
    )
  }

  override def createDefault: Option[T] = assetRegistry.createDefault
}
