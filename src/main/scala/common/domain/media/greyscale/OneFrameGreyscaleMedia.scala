package common.domain.media.greyscale

import common.domain.frame.greyscale.GreyscaleFrame

class OneFrameGreyscaleMedia(private val frame: GreyscaleFrame) extends GreyscaleMedia {
  override def getFrames: Seq[GreyscaleFrame] = Seq(frame)
}
