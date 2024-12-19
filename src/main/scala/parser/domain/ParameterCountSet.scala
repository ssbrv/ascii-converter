package parser.domain

class ParameterCountSet(private val allowedParameterCounts: Set[Int] = Set(0)) extends ParameterCount {
  private val whatIsAllowed = s"[${allowedParameterCounts.mkString(", ")}]"
  
  override def isAllowed(parameterCount: Int): Boolean = allowedParameterCounts.contains(parameterCount)
  override def getWhatIsAllowed: String = whatIsAllowed
}
