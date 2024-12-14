package parser.domain

case class AssetHandler[T](createCallback: Seq[String] => T, allowedParamCount: Set[Integer] = Set(0))


object AssetHandler {
  private val ANY_COUNT_FLAG: Int = -1

  def anyCount(): Set[Integer] = Set(ANY_COUNT_FLAG)
  def isAnyCountAllowed(allowedParamCount : Set[Integer]): Boolean = allowedParamCount.size == 1 && allowedParamCount.contains(ANY_COUNT_FLAG)

  def fromMinToAny(min: Int): Set[Integer] = Set(ANY_COUNT_FLAG, min)
  def isFromMinToAnyAllowed(allowedParamCount : Set[Integer]): Boolean =
    allowedParamCount.size == 2 && allowedParamCount.contains(ANY_COUNT_FLAG)

  def getMin(allowedParamCount : Set[Integer]): Option[Integer] = {
    if (!isFromMinToAnyAllowed(allowedParamCount))
      return None

    allowedParamCount.find(_ != ANY_COUNT_FLAG)
  }
}
