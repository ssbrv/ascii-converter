package filter.service.ascii

import common.domain.frame.Frame
import common.domain.frame.ascii.AsciiFrame
import common.domain.media.ascii.AsciiMedia

class FontAspectRationFilter(private val x: Int, private val y: Int) extends AsciiFilter {
  override def apply(media: AsciiMedia): AsciiMedia = {
    val adjustedFrames = media.frames.map(frame => adjustFrame(frame))
    new AsciiMedia(adjustedFrames)
  }

  private def adjustFrame(frame: AsciiFrame): AsciiFrame = {
    val newWidth = (frame.width * y) / x
    val newHeight = (frame.height * x) / y

    val scaleX = frame.width.toDouble / newWidth
    val scaleY = frame.height.toDouble / newHeight

    val data = Array.tabulate(newHeight, newWidth) { (y, x) =>
      val originalX = (x * scaleX).toInt
      val originalY = (y * scaleY).toInt
      frame(originalX)(originalY)
    }.map(_.toSeq).toSeq
    
    new AsciiFrame(data)
  }
}
