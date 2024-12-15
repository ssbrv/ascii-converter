package common.domain.pixel

import org.scalatest.funsuite.AnyFunSuite

class PixelTest extends AnyFunSuite {
  def testValidPixel(red: Int, green: Int, blue: Int): Unit = {
    test(s"Constructing pixel ($red, $green, $blue) succeeds") {
      val pixel = new Pixel(red, green, blue)
      assert(pixel.red == red)
      assert(pixel.green == green)
      assert(pixel.blue == blue)
    }
  }

  testValidPixel(200, 200, 200)
  testValidPixel(0, 0, 0)
  testValidPixel(255, 255, 255)


  def testValidPixelColorModel(rgb: Int, red: Int, green: Int, blue: Int): Unit = {
    test(s"Constructing pixel ($rgb) succeeds and RGB values match ($red, $green, $blue)") {
      val pixel = new Pixel(rgb)
      assert(pixel.red == red)
      assert(pixel.green == green)
      assert(pixel.blue == blue)
    }
  }

  testValidPixelColorModel(0xC8C8C8, 200, 200, 200)
  testValidPixelColorModel(0x000000, 0, 0, 0)
  testValidPixelColorModel(0xFFFFFF,  255, 255, 255)


  def testInvalidPixel(red: Int, green: Int, blue: Int): Unit = {
    test(s"Constructing pixel ($red, $green, $blue) throws an exception") {
      assertThrows[Exception] {
        new Pixel(red, green, blue)
      }
    }
  }

  testInvalidPixel(-1, 200, 200)
  testInvalidPixel(256, 200, 200)
  testInvalidPixel(200, -1, 200)
  testInvalidPixel(200, 256, 200)
  testInvalidPixel(200, 200, -1)
  testInvalidPixel(200, 200, 256)


//  def testGreyscale(red: Int, green: Int, blue: Int, greyscale: Int): Unit = {
//    test(s"Greyscale of pixel ($red, $green, $blue) is ${greyscale}") {
//      val pixel = new Pixel(red, green, blue)
//      assert(pixel.greyscale == greyscale)
//    }
//  }
//
//  testGreyscale(200, 200, 200, 200)
//  testGreyscale(0, 0, 0, 0)
//  testGreyscale(255, 255, 255, 255)
//  testGreyscale(200, 255, 0, 210)
//  testGreyscale(255, 200, 0, 194)
//  testGreyscale(0, 255, 200, 172)
}