package service.converter

import domain.frame.Frame
import domain.frame.ascii.Ascii
import domain.frame.pixel.media.Media

trait Converter {
  def convert(greyscale: Int): Char
  def convertToAscii(media: Media): Ascii = {
    new Ascii(
      for (frame <- media.getFrames) yield
        new Frame(
          for (y <- 0 until frame.getHeight) yield
            for (x <- 0 until frame.getWidth) yield
              convert(frame(x)(y).greyscale)
        )
    )
  }
}
