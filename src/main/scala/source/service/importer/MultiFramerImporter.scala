package source.service.importer

import common.domain.media.pixel.{MultiFramePixelMedia, PixelMedia}

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.imageio.metadata.{IIOMetadata, IIOMetadataNode}
import javax.imageio.stream.ImageInputStream
import scala.collection.mutable.ArrayBuffer
import scala.util.boundary

class MultiFramerImporter(private val file: File) extends FileImporter {
  override def provideMedia(): PixelMedia = {
    try {
      createMultiFramePixelMedia
    } catch {
      case e: Exception =>
        throw new RuntimeException(s"Error processing GIF file: ${file.getAbsolutePath}", e)
    }
  }

  private def createMultiFramePixelMedia: MultiFramePixelMedia = {
    val inputStream: ImageInputStream = ImageIO.createImageInputStream(file)
    val readers = ImageIO.getImageReaders(inputStream)

    if (!readers.hasNext)
      throw new IllegalArgumentException(s"Cannot read from file: ${file.getAbsolutePath}")

    val reader = readers.next()
    reader.setInput(inputStream)

    val frameCount = reader.getNumImages(true)
    val canvasSize = getCanvasSize(reader)
    val persistentCanvas = new BufferedImage(canvasSize._1, canvasSize._2, BufferedImage.TYPE_INT_ARGB)
    val bufferedImages = ArrayBuffer[BufferedImage]()
    val frameDelays = ArrayBuffer[Int]()

    val g = persistentCanvas.createGraphics()

    for (i <- 0 until frameCount) {
      val frame = reader.read(i, reader.getDefaultReadParam)
      val frameMetadata = reader.getImageMetadata(i)
      val delay = extractFrameDelay(frameMetadata)

      compositeFrame(g, frame, frameMetadata)
      val snapshot = new BufferedImage(canvasSize._1, canvasSize._2, BufferedImage.TYPE_INT_ARGB)
      val snapshotGraphics = snapshot.createGraphics()
      snapshotGraphics.drawImage(persistentCanvas, 0, 0, null)
      snapshotGraphics.dispose()

      bufferedImages += snapshot
      frameDelays += delay
    }

    g.dispose()
    reader.dispose()
    inputStream.close()

    if (bufferedImages.isEmpty)
      throw new IllegalArgumentException(s"Empty GIF in file: ${file.getAbsolutePath}")

    new MultiFramePixelMedia(bufferedImages.toSeq, calculateAverageFrameDelay(frameDelays.toSeq))
  }

  private def compositeFrame(
                              g: Graphics2D,
                              frame: BufferedImage,
                              metadata: IIOMetadata
                            ): Unit = {
    val imageDescriptorNode = getNode(
      metadata.getAsTree(metadata.getNativeMetadataFormatName).asInstanceOf[IIOMetadataNode],
      "ImageDescriptor"
    )

    val xOffset = imageDescriptorNode.getAttribute("imageLeftPosition").toInt
    val yOffset = imageDescriptorNode.getAttribute("imageTopPosition").toInt

    g.drawImage(frame, xOffset, yOffset, null)
  }

  private def getCanvasSize(reader: javax.imageio.ImageReader): (Int, Int) = {
    val metadata = reader.getStreamMetadata
    val metaFormatName = metadata.getNativeMetadataFormatName
    val root = metadata.getAsTree(metaFormatName).asInstanceOf[IIOMetadataNode]
    val screenDescriptorNode = getNode(root, "LogicalScreenDescriptor")

    val width = screenDescriptorNode.getAttribute("logicalScreenWidth").toInt
    val height = screenDescriptorNode.getAttribute("logicalScreenHeight").toInt
    (width, height)
  }

  private def calculateAverageFrameDelay(frameDelays: Seq[Int]): Int = {
    if (frameDelays.isEmpty) return 0
    (frameDelays.sum.toDouble / frameDelays.size).toInt
  }

  private def extractFrameDelay(metadata: IIOMetadata): Int = {
    val metaFormatName = metadata.getNativeMetadataFormatName
    val root: IIOMetadataNode = metadata.getAsTree(metaFormatName).asInstanceOf[IIOMetadataNode]
    val graphicsControlExtensionNode: IIOMetadataNode = getNode(root, "GraphicControlExtension")
    graphicsControlExtensionNode.getAttribute("delayTime").toInt
  }

  private def getNode(rootNode: IIOMetadataNode, nodeName: String): IIOMetadataNode = {
    boundary {
      val nNodes = rootNode.getLength

      for (i <- 0 until nNodes) {
        if (rootNode.item(i).getNodeName.compareToIgnoreCase(nodeName) == 0) {
          boundary.break(rootNode.item(i).asInstanceOf[IIOMetadataNode])
        }
      }

      val node = new IIOMetadataNode(nodeName)
      rootNode.appendChild(node)
      node
    }
  }
}
