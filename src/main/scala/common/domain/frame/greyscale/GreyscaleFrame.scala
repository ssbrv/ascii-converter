package common.domain.frame.greyscale

import common.domain.frame.Frame

class GreyscaleFrame(data: Seq[Seq[Int]]) extends Frame[Int](data) {
  require(frame.forall(_.forall(greyscale => greyscale >= 0 && greyscale <= 255)), s"Invalid greyscale value: ${frame.toString()}")
}
