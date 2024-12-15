package common.domain.media.pixel

import common.domain.frame.pixel.PixelFrame
import common.domain.pixel.Pixel

import java.awt.image.BufferedImage

class Image(private val frame: PixelFrame) extends PixelMedia {

  def this(bufferedImage: BufferedImage) = this(
    new PixelFrame(
      for (y <- 0 until bufferedImage.getHeight)
        yield
          for (x <- 0 until bufferedImage.getWidth)
            yield new Pixel(bufferedImage.getRGB(x, y))
    )
  )

  def this(pixels: Seq[Seq[Pixel]]) = this(new PixelFrame(pixels))

  override def getFrames: Seq[PixelFrame] = Seq(frame)
}
