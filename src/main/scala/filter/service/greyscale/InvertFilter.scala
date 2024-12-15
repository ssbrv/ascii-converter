package filter.service.greyscale

import common.domain.frame.greyscale.GreyscaleFrame
import common.domain.greyscale.GreyscaleValue
import common.domain.media.greyscale.GreyscaleMedia

class InvertFilter extends GreyscaleMediaFilter {
  override def apply(media: GreyscaleMedia): GreyscaleMedia = {
    val frames =
      for (frame <- media.frames) yield
        new GreyscaleFrame(
          for (y <- 0 until frame.height) yield
            for (x <- 0 until frame.width) yield
              invert(frame(x)(y))
        )

    new GreyscaleMedia(frames, media.frameDelay)
  }

  private def invert(greyscale: GreyscaleValue): GreyscaleValue = GreyscaleValue(GreyscaleValue.MAX_VALUE - greyscale.value)
}
