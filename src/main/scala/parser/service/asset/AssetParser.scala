package parser.service.asset

import converter.service.AsciiConverter
import exporter.service.AsciiExporter
import filter.ascii.service.AsciiFilter
import parser.domain.Command
import source.service.MediaSource

trait AssetParser {
  def parseArgumentsIntoAssets(commands: Seq[Command]): (MediaSource, AsciiConverter, Seq[AsciiFilter], AsciiExporter)
}
