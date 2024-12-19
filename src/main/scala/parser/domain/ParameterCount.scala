package parser.domain

trait ParameterCount {
  def isAllowed(parameterCount: Int): Boolean
  def getWhatIsAllowed: String
}
