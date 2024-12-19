package parser.domain

import org.scalatest.funsuite.AnyFunSuite

class ParameterCountSingleTest extends AnyFunSuite {

  test("isAllowed returns true for allowed parameter count and false for other parameters") {
    val parameterCountSingle = new ParameterCountSingle(5)
    assert(parameterCountSingle.isAllowed(5))
    assert(!parameterCountSingle.isAllowed(1))
  }

  test("whatIsAllowed returns correct string") {
    val parameterCountSingle = new ParameterCountSingle(5)
    assert(parameterCountSingle.getWhatIsAllowed == "5")
  }

  test("Defaults to allowing 0") {
    val parameterCountSingle = new ParameterCountSingle
    assert(parameterCountSingle.isAllowed(0))
    assert(!parameterCountSingle.isAllowed(1))
  }
}
