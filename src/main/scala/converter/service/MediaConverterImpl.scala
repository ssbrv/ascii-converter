package converter.service

import common.domain.frame.Frame
import common.domain.media.Media

abstract class MediaConverterImpl[F, T] extends MediaConverter[F, T] {
  override def convert(media: Media[F]): Media[T] = {
    new Media[T]:
      override def getFrames: Seq[Frame[T]] =
        for (frame <- media.getFrames) yield
          new Frame(
            for (y <- 0 until frame.getHeight) yield
              for (x <- 0 until frame.getWidth) yield
                convert(frame(x)(y))
          )
  }

  protected def convert(value: F): T
}
