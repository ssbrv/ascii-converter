package common.domain.media.greyscale

import common.domain.frame.greyscale.GreyscaleFrame
import common.domain.media.Media

trait GreyscaleMedia extends Media[Int] {
  override def getFrames: Seq[GreyscaleFrame]
}
