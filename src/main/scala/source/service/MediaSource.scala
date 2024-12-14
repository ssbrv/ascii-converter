package source.service

import common.domain.frame.pixel.media.Media

trait MediaSource {
  def provideMedia(): Media
}
