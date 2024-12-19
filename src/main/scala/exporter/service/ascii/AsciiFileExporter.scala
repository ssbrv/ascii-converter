package exporter.service.ascii

import common.domain.frame.ascii.AsciiFrame
import common.domain.media.ascii.AsciiMedia

import java.io.{File, FileWriter, IOException}
import java.nio.file.Files

class AsciiFileExporter(private val file: File) extends AsciiExporter {

  override def exportMedia(ascii: AsciiMedia): Unit = {
    if (ascii.frames.isEmpty)
      throw new IllegalArgumentException("No ascii art was produced from given source.")

    if (file.exists() && !isPlainTextFile(file))
      throw new IllegalArgumentException(s"File '${file.getName}' already exists and is not a plain text file.")

    if (!file.exists() && !file.createNewFile())
      throw new IOException(s"Failed to create new file: '${file.getAbsolutePath}'")

    val writer = new FileWriter(file)

    try {
      if (ascii.frames.length == 1) {
        printFrame(ascii.frames.last, writer)
        writer.close()
        return
      }

      for (frame <- ascii) {
        printFrameMetadata(frame, writer)
        printFrame(frame, writer)
      }
    }
    catch {
      case e: Exception =>
        throw new RuntimeException("Unexpected error occurred while exporting multi frame ascii art to file", e)
    } finally {
      writer.close()
    }
  }

  private def printFrameMetadata(frame: AsciiFrame, writer: FileWriter): Unit = {
    writer.append(s"===== ${frame.delay} =====\n")
  }

  private def printFrame(frame: AsciiFrame, writer: FileWriter): Unit = {
    for (row <- frame) {
      writer.append(row.mkString)
      writer.append('\n')
    }
  }

  private def isPlainTextFile(file: File): Boolean = {
    try {
      val mimeType = Files.probeContentType(file.toPath)
      mimeType != null && mimeType.startsWith("text/")
    } catch {
      case _: IOException => false
    }
  }
}
