package parser.domain

class ParameterCountSingle(private val allowedParameterCount: Int = 0) extends ParameterCount {
  private val whatIsAllowed = allowedParameterCount.toString
  
  override def isAllowed(parameterCount: Int): Boolean =
    allowedParameterCount == parameterCount

  override def getWhatIsAllowed: String = whatIsAllowed
}
