package common.domain.media

import common.domain.frame.Frame

trait Media[T] {
  def getFrames: Seq[Frame[T]]
}
