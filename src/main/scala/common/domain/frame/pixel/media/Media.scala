package common.domain.frame.pixel.media

import common.domain.frame.pixel.PixelFrame

trait Media {
  def getFrames: Seq[PixelFrame]
}