package common.domain.frame.pixel.media

import common.domain.frame.pixel.{Pixel, PixelFrame}

import java.awt.image.BufferedImage

class MultiFramer(bufferedImages: Seq[BufferedImage]) extends Media {
  private val frames: Seq[PixelFrame] = bufferedImages.map { bufferedImage =>
    val pixels = (0 until bufferedImage.getHeight).map { y =>
      (0 until bufferedImage.getWidth).map { x =>
        new Pixel(bufferedImage.getRGB(x, y))
      }
    }
    new PixelFrame(pixels)
  }

  override def getFrames: Seq[PixelFrame] = frames
}
