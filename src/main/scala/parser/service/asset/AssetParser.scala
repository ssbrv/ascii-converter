package parser.service.asset

import converter.service.ascii.AsciiConverter
import exporter.service.ascii.AsciiExporter
import filter.service.ascii.AsciiFilter
import filter.service.greyscale.GreyscaleMediaFilter
import parser.domain.Command
import source.service.MediaSource

trait AssetParser {
  def parseArgumentsIntoAssets(commands: Seq[Command]): (MediaSource, AsciiConverter, Seq[GreyscaleMediaFilter], Seq[AsciiFilter], AsciiExporter)
}
