package converter.service.ascii.linear

import common.domain.greyscale.GreyscaleValue
import org.scalatest.funsuite.AnyFunSuite

class LinearAsciiConverterTest extends AnyFunSuite {

  test("Throws an exception for null gradient") {
    assertThrows[IllegalArgumentException] {
      new LinearAsciiConverter(null)
    }
  }

  test("Throws an exception for empty gradient") {
    assertThrows[IllegalArgumentException] {
      new LinearAsciiConverter("")
    }
  }

  test("Throws an exception for gradient longer than 256 characters") {
    val longGradient = "a" * 257
    assertThrows[IllegalArgumentException] {
      new LinearAsciiConverter(longGradient)
    }
  }

  def testGradient(gradient: String): Unit = {
    test(s"Test all greyscale values for gradient: $gradient") {
      val step = 256.0 / gradient.length

      val expectedResult = Array.tabulate(256) { greyscaleValue =>
        val index = Math.min((greyscaleValue / step).toInt, gradient.length - 1)
        gradient.charAt(index)
      }

      val converter = new LinearAsciiConverter(gradient)

      for (greyscaleValue <- 0 to 255)
        assert(converter.convert(GreyscaleValue(greyscaleValue)) == expectedResult(greyscaleValue))
    }
  }

  testGradient("abcd")
  testGradient("123456")
  testGradient("1".repeat(100))
  testGradient("1234".repeat(64))
}
