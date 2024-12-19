package parser.domain

import org.scalatest.funsuite.AnyFunSuite

class ParameterCountSetTest extends AnyFunSuite {

  def testIsAllowedParameterCount(parameterCountSet: ParameterCountSet, parameterCount: Int): Unit = {
    test(s"isAllowed returns true for allowed parameter count ($parameterCount from ${parameterCountSet.getWhatIsAllowed})") {
      assert(parameterCountSet.isAllowed(parameterCount))
    }
  }

  def testIsNotAllowedParameterCount(parameterCountSet: ParameterCountSet, parameterCount: Int): Unit = {
    test(s"isAllowed returns false for not allowed parameter count ($parameterCount is not from ${parameterCountSet.getWhatIsAllowed})") {
      assert(!parameterCountSet.isAllowed(parameterCount))
    }
  }

  def testAll(allowed: Set[Int], notAllowedExamples: Set[Int], whatIsAllowed: String): Unit = {
    val parameterCountSet = new ParameterCountSet(allowed)

    for (parameterCount <- allowed)
      testIsAllowedParameterCount(parameterCountSet, parameterCount)

    for (parameterCount <- notAllowedExamples)
      testIsNotAllowedParameterCount(parameterCountSet, parameterCount)

    test(s"whatIsAllowed returns correct string ($whatIsAllowed)") {
      assert(parameterCountSet.getWhatIsAllowed == whatIsAllowed)
    }
  }

  testAll(Set(1, 2, 3), Set(0, 4), "[1, 2, 3]")
  testAll(Set(1), Set(0), "[1]")

  test("Defaults to only allowing 0") {
    val parameterCountSet = new ParameterCountSet
    assert(parameterCountSet.isAllowed(0))
    assert(!parameterCountSet.isAllowed(1))
    assert(parameterCountSet.getWhatIsAllowed == "[0]")
  }
}
