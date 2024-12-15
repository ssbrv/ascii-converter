package filter.service.greyscale

import common.domain.frame.Frame
import common.domain.media.Media

class InvertFilter extends GreyscaleMediaFilter {
  override def apply(media: Media[Int]): Media[Int] = {
    new Media[Int]:
      override def getFrames: Seq[Frame[Int]] =
        for (frame <- media.getFrames) yield
          new Frame(
            for (y <- 0 until frame.getHeight) yield
              for (x <- 0 until frame.getWidth) yield
                invert(frame(x)(y))
          )
  }

  private def invert(greyscale: Int) = 255 - greyscale
}
