package domain.frame.ascii

import domain.frame.Frame

class Ascii(private val asciiFrames: Seq[Frame[Char]]) {
  def getAsciiFrames: Seq[Frame[Char]] = asciiFrames
}
