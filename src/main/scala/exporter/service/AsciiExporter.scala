package exporter.service

import common.domain.frame.ascii.Ascii

trait AsciiExporter {
  def exportAscii(ascii: Ascii): Unit
}
