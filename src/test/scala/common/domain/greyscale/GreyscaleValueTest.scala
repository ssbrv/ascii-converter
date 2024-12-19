package common.domain.greyscale

import org.scalatest.funsuite.AnyFunSuite

class GreyscaleValueTest extends AnyFunSuite {

  def testValid(value: Int): Unit = {
    test(s"Creating a valid greyscale value of $value succeeds") {
      assert(GreyscaleValue(value).value == value)
    }
  }

  testValid(GreyscaleValue.MIN_VALUE)
  testValid(100)
  testValid(GreyscaleValue.MAX_VALUE)

  def testInvalid(value: Int): Unit = {
    test(s"Creating an invalid greyscale value of $value throws an exception") {
      assertThrows[IllegalArgumentException] {
        GreyscaleValue(value)
      }
    }
  }

  testInvalid(-1)
  testInvalid(256)
}
