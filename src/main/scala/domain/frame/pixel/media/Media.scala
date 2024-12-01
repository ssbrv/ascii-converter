package domain.frame.pixel.media

import domain.frame.pixel.PixelFrame

trait Media {
  def getFrames: Seq[PixelFrame]
}