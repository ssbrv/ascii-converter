package common.domain.media.greyscale

import common.domain.frame.greyscale.GreyscaleFrame

class MultiFrameGreyscaleMedia(private val frames: Seq[GreyscaleFrame]) extends GreyscaleMedia {
  override def getFrames: Seq[GreyscaleFrame] = frames
}
