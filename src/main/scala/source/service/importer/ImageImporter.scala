package source.service.importer

import common.domain.media.pixel.{Image, PixelMedia}

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO;

class ImageImporter(private val file: File) extends FileImporter {
  override def provideMedia(): PixelMedia = {
    try {
      val bufferedImage: BufferedImage = Option(ImageIO.read(file))
        .getOrElse(throw new IllegalArgumentException(s"Corrupt file: ${file.getAbsolutePath}"))

      new Image(bufferedImage)
    } catch {
      case e: Exception =>
        throw new RuntimeException(s"Error reading image file: ${file.getAbsolutePath}", e)
    }
  }
}
