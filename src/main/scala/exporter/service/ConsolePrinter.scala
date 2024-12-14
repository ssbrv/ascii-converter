package exporter.service

import common.domain.frame.Frame
import common.domain.frame.ascii.Ascii

class ConsolePrinter extends AsciiExporter {
  override def exportAscii(ascii: Ascii): Unit = {
    val asciiFrames = ascii.getAsciiFrames

    if (asciiFrames.isEmpty)
      return

    if (asciiFrames.length == 1) {
      printAsciiFrame(asciiFrames.last)
      return
    }

    animateAsciiFrames(asciiFrames)
  }

  private def printAsciiFrame(asciiFrame: Frame[Char]): Unit = {
    for (row <- asciiFrame)
      println(row.mkString)
  }

  private def animateAsciiFrames(asciiFrames: Seq[Frame[Char]]): Unit = {
    for (frame <- asciiFrames) {
      clearConsole()
      printAsciiFrame(frame)
      Thread.sleep(200)
    }
  }

  private def clearConsole(): Unit = {
    print("\u001b[H\u001b[2J")
    System.out.flush()
  }
}
