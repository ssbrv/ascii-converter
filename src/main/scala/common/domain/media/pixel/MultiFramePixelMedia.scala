package common.domain.media.pixel

import common.domain.frame.pixel.PixelFrame
import common.domain.pixel.Pixel

import java.awt.image.BufferedImage

class MultiFramePixelMedia(bufferedImages: Seq[BufferedImage], frameDelay: Int) extends PixelMedia(
  bufferedImages.map { bufferedImage =>
    val pixels = (0 until bufferedImage.getHeight).map { y =>
      (0 until bufferedImage.getWidth).map { x =>
        new Pixel(bufferedImage.getRGB(x, y))
      }
    }
    new PixelFrame(pixels)
  },
  frameDelay
)
