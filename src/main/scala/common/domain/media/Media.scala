package common.domain.media

import common.domain.frame.Frame

case class Media[T](frames: Seq[Frame[T]]) extends Iterable[Frame[T]] {
  override def iterator: Iterator[Frame[T]] = frames.iterator
}