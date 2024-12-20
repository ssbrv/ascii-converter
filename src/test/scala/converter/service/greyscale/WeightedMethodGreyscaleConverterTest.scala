package converter.service.greyscale

import common.domain.frame.pixel.PixelFrame
import common.domain.media.pixel.PixelMedia
import common.domain.pixel.Pixel
import org.scalatest.funsuite.AnyFunSuite

class WeightedMethodGreyscaleConverterTest extends AnyFunSuite {
  val converter = new WeightedMethodGreyscaleConverter()

  def formula(pixel: Pixel): Int = (0.3 * pixel.red + 0.59 * pixel.green + 0.11 * pixel.blue).toInt

  def testConversion(pixelFrame: PixelFrame): Unit = {
    val pixelMedia = new PixelMedia(Seq(pixelFrame))

    val greyscaleMedia = converter.convert(pixelMedia)

    for ((frame, index) <- pixelMedia.frames.zipWithIndex) {
      val greyscaleFrame = greyscaleMedia.frames(index)

      assert(greyscaleFrame.delay == frame.delay)
      assert(greyscaleFrame.width == frame.width)
      assert(greyscaleFrame.height == frame.height)

      for (y <- 0 until frame.height) {
        for (x <- 0 until frame.width) {
          val greyscaleValue = greyscaleFrame(x)(y)
          val expectedGreyscaleValue = formula(frame(x)(y))
          assert(expectedGreyscaleValue == greyscaleValue.value)
        }
      }
    }
  }

  test("converts so that each greyscale corresponds to formula(pixel)") {
    testConversion(new PixelFrame(
      Seq(
        Seq(new Pixel(10, 10, 10), new Pixel(20, 20, 20), new Pixel(30, 30, 30)),
        Seq(new Pixel(11, 11, 11), new Pixel(21, 21, 21), new Pixel(31, 31, 31)),
        Seq(new Pixel(100, 100, 100), new Pixel(200, 200, 200), new Pixel(230, 230, 230)),
        Seq(new Pixel(10, 10, 10), new Pixel(20, 20, 20), new Pixel(130, 130, 130))
      )
    ))

    testConversion(new PixelFrame(
      Seq(
        Seq(new Pixel(10, 10, 10), new Pixel(20, 20, 20), new Pixel(30, 30, 30)),
        Seq(new Pixel(11, 11, 11), new Pixel(21, 21, 21), new Pixel(31, 31, 31)),
      )
    ))

    testConversion(new PixelFrame(
      Seq(
        Seq(new Pixel(10, 10, 10)),
        Seq(new Pixel(11, 11, 11)),
        Seq(new Pixel(100, 100, 100)),
        Seq(new Pixel(10, 10, 10))
      )
    ))

    testConversion(new PixelFrame())
  }

  test("converts empty media") {
    testConversion(new PixelFrame())
  }
}
