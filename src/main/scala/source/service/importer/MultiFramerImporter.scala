package source.service.importer

import common.domain.frame.pixel.media.{Media, MultiFramer}
import source.service.MediaSource

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.imageio.stream.ImageInputStream
import scala.collection.mutable.ArrayBuffer

class MultiFramerImporter(private val file: File) extends MediaSource {
  override def provideMedia(): Media = {
    try {
      val bufferedImages = extractBufferedImages
      
      if (bufferedImages.isEmpty)
        throw new IllegalArgumentException(s"Empty GIF in file: ${file.getAbsolutePath}")
      
      new MultiFramer(bufferedImages)
    } catch {
      case e: Exception =>
        throw new RuntimeException(s"Error processing GIF file: ${file.getAbsolutePath}", e)
    }
  }

  private def extractBufferedImages: Seq[BufferedImage] = {
    val inputStream: ImageInputStream = ImageIO.createImageInputStream(file)
    val readers = ImageIO.getImageReaders(inputStream)

    if (!readers.hasNext)
      throw new IllegalArgumentException(s"Cannot read from file: ${file.getAbsolutePath}")

    val reader = readers.next()
    reader.setInput(inputStream)

    val frameCount = reader.getNumImages(true)
    val bufferedImages = ArrayBuffer[BufferedImage]()

    for (i <- 0 until frameCount)
      bufferedImages += reader.read(i)

    reader.dispose()
    inputStream.close()

    bufferedImages.toSeq
  }
}
