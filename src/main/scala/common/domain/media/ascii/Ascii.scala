package common.domain.media.ascii

import common.domain.frame.Frame
import common.domain.media.Media

class Ascii(private val asciiFrames: Seq[Frame[Char]]) extends Media[Char] {
  override def getFrames: Seq[Frame[Char]] = asciiFrames
}
