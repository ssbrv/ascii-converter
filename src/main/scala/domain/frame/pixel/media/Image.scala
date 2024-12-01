package domain.frame.pixel.media

import domain.frame.pixel.{Pixel, PixelFrame}

import java.awt.image.BufferedImage

class Image(bufferedImage: BufferedImage) extends Media {
  private val frame: PixelFrame =
    new PixelFrame(
      for (y <- 0 until bufferedImage.getHeight)
        yield
          for (x <- 0 until bufferedImage.getWidth)
            yield new Pixel(bufferedImage.getRGB(x, y)))

  override def getFrames: Seq[PixelFrame] = Seq(frame)
}
