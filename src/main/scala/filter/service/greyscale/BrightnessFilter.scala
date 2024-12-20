package filter.service.greyscale

import common.domain.frame.greyscale.GreyscaleFrame
import common.domain.greyscale.GreyscaleValue
import common.domain.media.greyscale.GreyscaleMedia

class BrightnessFilter(val brightnessDelta: Int) extends GreyscaleMediaFilter {
  override def apply(media: GreyscaleMedia): GreyscaleMedia = {
    val frames =
      for (frame <- media.frames) yield
        new GreyscaleFrame(
          for (y <- 0 until frame.height) yield
            for (x <- 0 until frame.width) yield
              applyBrightnessDelta(frame(x)(y))
          ,
          frame.delay
        )

    new GreyscaleMedia(frames)
  }

  private def applyBrightnessDelta(greyscale: GreyscaleValue): GreyscaleValue = {
    val newGreyscaleValue = greyscale.value + brightnessDelta

    if (newGreyscaleValue < GreyscaleValue.MIN_VALUE)
      return GreyscaleValue(GreyscaleValue.MIN_VALUE)

    if (newGreyscaleValue > GreyscaleValue.MAX_VALUE)
      return GreyscaleValue(GreyscaleValue.MAX_VALUE)

    GreyscaleValue(newGreyscaleValue)
  }
}
