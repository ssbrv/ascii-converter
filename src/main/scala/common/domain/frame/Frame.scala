package common.domain.frame

case class Frame[T](frame: Seq[Seq[T]] = Seq()) extends Iterable[Seq[T]] {
  val width: Int = if (frame.nonEmpty) frame.head.length else 0
  val height: Int = frame.length

  require(frame.forall(_.length == width), "All rows must have the same length")

  def apply(x: Int)(y: Int): T = frame(y)(x)

  override def iterator: Iterator[Seq[T]] = frame.iterator
}
