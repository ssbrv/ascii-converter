package exporter.service

import common.domain.frame.ascii.Ascii

import java.io.{File, FileWriter, IOException}
import java.nio.file.Files

class FileExporter(private val file: File) extends AsciiExporter {

  override def exportAscii(ascii: Ascii): Unit = {
    if (ascii.getAsciiFrames.isEmpty)
      throw new IllegalArgumentException("No ascii art was produced from given source.")

    if (ascii.getAsciiFrames.length > 1)
      throw new IllegalArgumentException("Multiframe ascii art export is not supported yet")

    val asciiFrame = ascii.getAsciiFrames.last

    if (file.exists() && !isPlainTextFile(file))
      throw new IllegalArgumentException(s"File '${file.getName}' already exists and is not a plain text file.")

    if (!file.exists() && !file.createNewFile())
      throw new IOException(s"Failed to create new file: '${file.getAbsolutePath}'")

    val writer = new FileWriter(file)
    try {
      writer.write("")
      for (row <- asciiFrame) {
        writer.append(row.mkString)
        writer.append('\n')
      }
    } finally {
      writer.close()
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
