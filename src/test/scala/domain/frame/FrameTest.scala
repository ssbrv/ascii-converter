package domain.frame

import org.scalatest.funsuite.AnyFunSuite

class FrameTest extends AnyFunSuite {
  def testValidFrame[T](frameData: Seq[Seq[T]], width: Int, height: Int): Unit = {
    test(s"Constructing a $width X $height frame succeeds and content matches") {
      val frame = new Frame(frameData)
      assert(frame.getWidth == width)
      assert(frame.getHeight == height)
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

  test("Constructing a Frame with unequal-length rows throws an exception") {
    val frameData = Seq(
      Seq(1, 2),
      Seq(3, 4, 5)
    )
    assertThrows[IllegalArgumentException] {
      new Frame(frameData)
    }
  }

  test("Accessing elements within bounds using apply succeeds") {
    val frameData = Seq(
      Seq(1, 2, 3),
      Seq(4, 5, 6),
      Seq(7, 8, 9)
    )
    val frame = new Frame(frameData)
    assert(frame(1)(0) == 2)
    assert(frame(2)(2) == 9)
  }

  test("Accessing elements out of bounds throws an exception") {
    val frameData = Seq(
      Seq(1, 2, 3),
      Seq(4, 5, 6)
    )
    val frame = new Frame(frameData)

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
}