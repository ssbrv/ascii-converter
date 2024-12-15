package converter.service

import common.domain.media.Media

trait MediaConverter[F, T] {
  def convert(media: Media[F]): Media[T]
}
