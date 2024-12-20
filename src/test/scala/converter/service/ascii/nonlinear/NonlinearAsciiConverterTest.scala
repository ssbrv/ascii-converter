package converter.service.ascii.nonlinear

import org.scalatest.funsuite.AnyFunSuite
import common.domain.greyscale.GreyscaleValue

class NonlinearAsciiConverterTest extends AnyFunSuite {

  def assertGradientTable(converter: NonlinearAsciiConverter, greyscaleValues: Seq[Int], expectedResult: String): Unit = {
    for (index <- greyscaleValues.indices)
      assert(converter.convert(GreyscaleValue(greyscaleValues(index))) == expectedResult.charAt(index))
  }

  test("Converts greyscale values correctly") {
    val gradientTable = Seq(
      (GreyscaleValue(252), '.'), // For values 0-252, map to '.'
      (GreyscaleValue(253), ':'), // For values 253, map to ':'
      (GreyscaleValue(254), '*'), // For values 254, map to '*'
      (GreyscaleValue(255), '#') // For value 255, map to '#'
    )
    val converter = new NonlinearAsciiConverter(gradientTable)

    val greyscaleValues = Seq(1, 10, 100, 251, 252, 253, 254, 255)
    val expectedResult = ".....:*#"

    assertGradientTable(converter, greyscaleValues, expectedResult)
  }

  test("Throws exception for empty gradient table") {
    val gradientTable = Seq()
    assertThrows[Exception] {
      new NonlinearAsciiConverter(gradientTable)
    }
  }

  test("Throws exception for not complete gradient table (missing (255, char) record)") {
    val gradientTable = Seq(
      (GreyscaleValue(252), '.'), // For values 0-252, map to '.'
      (GreyscaleValue(253), ':'), // For values 253, map to ':'
    )
    assertThrows[Exception] {
      new NonlinearAsciiConverter(gradientTable)
    }
  }
}
