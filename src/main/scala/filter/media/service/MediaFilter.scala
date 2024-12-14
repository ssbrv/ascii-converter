package filter.media.service

import common.domain.frame.pixel.media.Media

trait MediaFilter {
  def apply(media: Media): Media
}
