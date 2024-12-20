package source.service.random

import org.scalatest.funsuite.AnyFunSuite

class RandomImageGeneratorTest extends AnyFunSuite {

  private val seed = 12345

  def assertWithStrictSize(w: Int, h: Int, seed: Long): Unit = {
    val generator = new RandomImageGenerator(w, w, h, h, seed)
    assertWithRange(w, w, h, h, seed)
  }

  def assertWithRange(minW: Int, maxW: Int, minH: Int, maxH: Int, seed: Long): Unit = {
    val generator = new RandomImageGenerator(minW, maxW, minH, maxH, seed)
    assertGenerator(generator, minW, maxW, minH, maxH)
  }

  def assertGenerator(generator: RandomImageGenerator, minW: Int, maxW: Int, minH: Int, maxH: Int): Unit = {
    val media = generator.provideMedia()
    assert(media.frames.length == 1)
    val frame = media.frames.last
    assert(frame.delay == 0)
    assert(minW <= frame.width)
    assert(minH <= frame.height)
    assert(frame.width <= maxW)
    assert(frame.height <= maxH)
  }

  def assertSameSeedSameResult(minW: Int, maxW: Int, minH: Int, maxH: Int, seed: Long): Unit = {
    val generator1 = new RandomImageGenerator(minW, maxW, minH, maxH, seed)
    val generator2 = new RandomImageGenerator(minW, maxW, minH, maxH, seed)

    assert(generator1.provideMedia() == generator2.provideMedia())
  }

  test("provideMedia returns media of size within range with seed specified") {
    assertWithRange(10, 100, 30, 300, seed)
    assertWithRange(100, 200, 300, 350, seed)
    assertWithRange(200, 250, 100, 150, seed)
  }

  test("provideMedia returns media of correct size when minW = maxW and minH = maxH") {
    assertWithStrictSize(500, 500, seed)
    assertWithStrictSize(100, 200, seed)
    assertWithStrictSize(200, 100, seed)
  }

  test("provideMedia returns media of correct size when minW = maxW = 1") {
    assertWithStrictSize(1, 500, seed)
    assertWithStrictSize(1, 200, seed)
    assertWithStrictSize(1, 100, seed)
    assertWithStrictSize(1, 250, seed)
  }

  test("provideMedia returns media of correct size when minY = maxY = 1") {
    assertWithStrictSize(500, 1, seed)
    assertWithStrictSize(200, 1, seed)
    assertWithStrictSize(100, 1, seed)
  }

  test("provideMedia returns media of correct size when minW = maxW = minY = maxY = 1") {
    assertWithStrictSize(1, 1, seed)
  }

  test("provideMedia returns same media for the same seed") {
    assertSameSeedSameResult(20, 50, 20, 50, seed)
    assertSameSeedSameResult(200, 500, 200, 500, seed)
    assertSameSeedSameResult(200, 500, 200, 500, 12341245)
  }

  // because it's not recommended to rely on testing RNG with random seed, we just check if this does not throw an exception
  test("provideMedia with no provided seed succeeds") {
    val generator = new RandomImageGenerator(10, 20, 10, 20)
    generator.provideMedia()
  }

  def assertInvalid(minW: Int, maxW: Int, minH: Int, maxH: Int, seed: Long): Unit = {
    assertThrows[Exception] {
      new RandomImageGenerator(minW, maxW, minH, maxH, seed)
    }
  }

  test("Throws an exception when invalid range provided for width") {
    assertInvalid(10, 5, 10, 20, seed)
  }

  test("Throws an exception when invalid range provided for height") {
    assertInvalid(10, 20, 10, 5, seed)
  }

  test("Throws an exception when non positive minW provided") {
    assertInvalid(-1, 20, 10, 50, seed)
  }

  test("Throws an exception when non positive maxW provided") {
    assertInvalid(20, 0, 10, 50, seed)
  }

  test("Throws an exception when non positive minH provided") {
    assertInvalid(20, 50, -5, 10, seed)
  }

  test("Throws an exception when non positive maxH provided") {
    assertInvalid(20, 50, 10, 0, seed)
  }
}
