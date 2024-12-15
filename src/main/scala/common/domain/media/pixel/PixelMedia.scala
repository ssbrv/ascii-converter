package common.domain.media.pixel

import common.domain.frame.pixel.PixelFrame
import common.domain.media.Media
import common.domain.pixel.Pixel

trait PixelMedia extends Media[Pixel] {
  override def getFrames: Seq[PixelFrame]
}