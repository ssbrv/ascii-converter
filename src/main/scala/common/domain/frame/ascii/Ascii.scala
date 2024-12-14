package common.domain.frame.ascii

import common.domain.frame.Frame

class Ascii(private val asciiFrames: Seq[Frame[Char]]) {
  def getAsciiFrames: Seq[Frame[Char]] = asciiFrames
}
