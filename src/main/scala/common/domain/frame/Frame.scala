package common.domain.frame

case class Frame[T](frame: Seq[Seq[T]] = Seq(), delay: Int = 0) extends Iterable[Seq[T]] {
  val width: Int = if (frame.nonEmpty) frame.head.length else 0
  val height: Int = frame.length

  require(frame.forall(_.length == width), "All rows must have the same length")
  require(delay >= 0, "Frame delay cannot be a negative number")

  def apply(x: Int)(y: Int): T = frame(y)(x)

  override def iterator: Iterator[Seq[T]] = frame.iterator
}
