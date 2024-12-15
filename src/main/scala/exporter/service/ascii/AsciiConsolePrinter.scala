package exporter.service.ascii

import common.domain.frame.ascii.AsciiFrame
import common.domain.media.ascii.AsciiMedia

class AsciiConsolePrinter extends AsciiExporter {
  override def exportMedia(ascii: AsciiMedia): Unit = {
    if (ascii.frames.isEmpty)
      return

    if (ascii.frames.length == 1) {
      printAsciiFrame(ascii.frames.last)
      return
    }

    animateAscii(ascii)
  }

  private def printAsciiFrame(asciiFrame: AsciiFrame): Unit = {
    for (row <- asciiFrame)
      println(row.mkString)
  }

  private def animateAscii(ascii: AsciiMedia): Unit = {
    for (frame <- ascii) {
      clearConsole()
      printAsciiFrame(frame)
      Thread.sleep(50) // TODO: replace with media frame rate
    }
  }

  private def clearConsole(): Unit = {
    print("\u001b[H\u001b[2J")
    System.out.flush()
  }
}
