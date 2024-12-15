package source.service

import common.domain.media.pixel.PixelMedia

trait MediaSource {
  def provideMedia(): PixelMedia
}
