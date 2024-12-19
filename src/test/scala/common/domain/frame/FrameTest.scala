package common.domain.frame

import org.scalatest.funsuite.AnyFunSuite

class FrameTest extends AnyFunSuite {
  def testValidFrame[T](frameData: Seq[Seq[T]], width: Int, height: Int, frameDelay: Int = 0): Unit = {
    test(s"Constructing a $width X $height frame with frame delay of $frameDelay succeeds and content matches") {
      val frame = Frame(frameData, frameDelay)
      assert(frame.width == width)
      assert(frame.height == height)
      assert(frame.delay == frameDelay)
    }
  }

  testValidFrame(Seq(
    Seq(1, 2, 3),
    Seq(4, 5, 6),
    Seq(7, 8, 9)
  ), 3, 3)

  testValidFrame(Seq(
    Seq(1, 2),
    Seq(4, 5),
    Seq(7, 8)
  ), 2, 3)

  testValidFrame(Seq(
    Seq(1, 2, 3),
    Seq(4, 5, 6)
  ), 3, 2)

  testValidFrame(Seq(), 0, 0)

  testValidFrame(Seq(), 0, 0, 1000)

  test("Constructing a Frame with unequal-length rows throws an exception") {
    val frameData = Seq(
      Seq(1, 2),
      Seq(3, 4, 5)
    )
    assertThrows[IllegalArgumentException] {
      Frame(frameData)
    }
  }

  test("Accessing elements within bounds using apply succeeds") {
    val frameData = Seq(
      Seq(1, 2, 3),
      Seq(4, 5, 6),
      Seq(7, 8, 9)
    )
    val frame = Frame(frameData)
    assert(frame(1)(0) == 2)
    assert(frame(2)(2) == 9)
  }

  test("Accessing elements out of bounds throws an exception") {
    val frameData = Seq(
      Seq(1, 2, 3),
      Seq(4, 5, 6)
    )
    val frame = Frame(frameData)

    assertThrows[Exception] {
      frame(-1)(0)
    }

    assertThrows[Exception] {
      frame(3)(0)
    }

    assertThrows[Exception] {
      frame(0)(2)
    }
  }

  test("Constructing a frame with negative frame delay throws an exception") {
    assertThrows[Exception] {
      Frame(Seq(), -1000)
    }
  }
}