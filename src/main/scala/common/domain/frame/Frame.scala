package common.domain.frame

class Frame[T](private val frame: Seq[Seq[T]]) extends Iterable[Seq[T]] {
  def this() = this(Seq())
  
  private val width: Int = if (frame.nonEmpty) frame.head.length else 0
  private val height: Int = frame.length

  require(frame.forall(_.length == width), "All rows must have the same length")

  def apply(x: Int)(y: Int): T = {
    validateXY(x, y)
    frame(y)(x)
  }
  
  private def validateXY(x: Int, y: Int): Unit = {
    require(x >= 0 && x < width, "x is out of bounds")
    require(y >= 0 && y < height, "y is out of bounds")
  }

  def getWidth: Int = width
  def getHeight: Int = height

  override def iterator: Iterator[Seq[T]] = frame.iterator
}
