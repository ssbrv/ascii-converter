package filter.service.ascii

import common.domain.media.Media
import common.domain.frame.Frame

class FontAspectRationFilter(private val x: Int, private val y: Int) extends AsciiFilter {
  override def apply(media: Media[Char]): Media[Char] = {
    val adjustedFrames = media.getFrames.map(frame => adjustFrame(frame))
    new Media[Char] {
      override def getFrames: Seq[Frame[Char]] = adjustedFrames
    }
  }

  private def adjustFrame(frame: Frame[Char]): Frame[Char] = {
    val originalWidth = frame.getWidth
    val originalHeight = frame.getHeight

    val newWidth = (originalWidth * y) / x
    val newHeight = (originalHeight * x) / y

    val scaledFrame = scaleFrame(frame, newWidth, newHeight)
    new Frame(scaledFrame)
  }

  private def scaleFrame(frame: Frame[Char], newWidth: Int, newHeight: Int): Seq[Seq[Char]] = {
    val originalWidth = frame.getWidth
    val originalHeight = frame.getHeight

    val scaleX = originalWidth.toDouble / newWidth
    val scaleY = originalHeight.toDouble / newHeight

    Array.tabulate(newHeight, newWidth) { (y, x) =>
      val originalX = (x * scaleX).toInt
      val originalY = (y * scaleY).toInt
      frame(originalX)(originalY)
    }.map(_.toSeq).toSeq
  }
}
