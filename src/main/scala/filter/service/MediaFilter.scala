package filter.service

import common.domain.media.Media

trait MediaFilter[T] {
  def apply(media: Media[T]): Media[T]
}
