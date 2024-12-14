package source.service.randomgenerator

import common.domain.frame.pixel.Pixel
import common.domain.frame.pixel.media.{Image, Media}
import source.service.MediaSource

import scala.util.Random

class RandomImageGenerator(private val minWidth: Int, private val maxWidth: Int, private val minHeight: Int, private val maxHeight: Int) extends MediaSource {
  
  require(minWidth >= 0, "Cannot setup random image generator: negative min width value")
  require(minHeight >= 0, "Cannot setup random image generator: negative min height value")

  require(maxWidth >= 0, "Cannot setup random image generator: negative max width value")
  require(maxHeight >= 0, "Cannot setup random image generator: negative max height value")
  
  require(minWidth <= maxWidth, s"Cannot setup random image generator: <$minWidth; $maxWidth> is an invalid range")
  require(minHeight <= maxHeight, s"Cannot setup random image generator: <$minHeight; $maxHeight> is an invalid range")
  
  override def provideMedia(): Media = {
    val random = new Random

    val width = minWidth + random.nextInt(maxWidth - minWidth + 1)
    val height = minHeight + random.nextInt(maxHeight - minHeight + 1)

    val pixels: Seq[Seq[Pixel]] = Seq.fill(height, width) {
      new Pixel(random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

    new Image(pixels)
  }
}
