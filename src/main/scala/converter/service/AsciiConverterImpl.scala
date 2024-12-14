package converter.service

import common.domain.frame.Frame
import common.domain.frame.ascii.Ascii
import common.domain.frame.pixel.media.Media

abstract class AsciiConverterImpl extends AsciiConverter {
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

  protected def convert(greyscale: Int): Char
}
