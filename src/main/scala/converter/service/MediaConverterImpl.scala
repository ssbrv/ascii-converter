package converter.service

import common.domain.frame.Frame
import common.domain.media.Media

abstract class MediaConverterImpl[F, T] extends MediaConverter[F, T] {
  override def convert(media: Media[F]): Media[T] = {
    val frames =
      for (frame <- media.frames) yield
        new Frame[T](
          for (y <- 0 until frame.height) yield
            for (x <- 0 until frame.width) yield
              convert(frame(x)(y))
        )

    new Media[T](frames)
  }
  
  protected def convert(value: F): T
}
