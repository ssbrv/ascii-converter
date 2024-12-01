package model.media

import domain.frame.pixel.Pixel
import org.scalatest.funsuite.AnyFunSuite

class PixelTest extends AnyFunSuite {
  test("Pixel creation: basic") {
    val pixel = new Pixel(200, 200, 200)
    assert(pixel.getRed == 200)
    assert(pixel.getGreen == 200)
    assert(pixel.getBlue == 200)
  }

  test("Pixel creation: lower bound case (0, 0, 0)") {
    val pixel = new Pixel(0, 0, 0)
    assert(pixel.getRed == 0)
    assert(pixel.getGreen == 0)
    assert(pixel.getBlue == 0)
  }

  test("Pixel creation: upper bound case (255, 255, 255)") {
    val pixel = new Pixel(255, 255, 255)
    assert(pixel.getRed == 255)
    assert(pixel.getGreen == 255)
    assert(pixel.getBlue == 255)
  }

  test("Pixel creation: invalid lower bound case (-1, 200, 200)") {
    assertThrows[IllegalArgumentException] {
      new Pixel(-1, 200, 200)
    }
  }

  test("Pixel creation: invalid lower bound case (200, -1, 200)") {
    assertThrows[IllegalArgumentException] {
      new Pixel(200, -1, 200)
    }
  }

  test("Pixel creation: invalid lower bound case (200, 200, -1)") {
    assertThrows[IllegalArgumentException] {
      new Pixel(200, 200, -1)
    }
  }

  test("Pixel creation: invalid upper bound case (256, 200, 200)") {
    assertThrows[IllegalArgumentException] {
      new Pixel(256, 200, 200)
    }
  }

  test("Pixel creation: invalid upper bound case (200, 256, 200)") {
    assertThrows[IllegalArgumentException] {
      new Pixel(200, 256, 200)
    }
  }

  test("Pixel creation: invalid upper bound case (200, 200, 256)") {
    assertThrows[IllegalArgumentException] {
      new Pixel(200, 200, 256)
    }
  }
}
