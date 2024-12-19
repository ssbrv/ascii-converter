package parser.service.asset

import common.mapper.AssetMapper
import converter.service.ascii.AsciiConverter
import exporter.service.ascii.AsciiExporter
import filter.service.ascii.AsciiFilter
import filter.service.greyscale.GreyscaleMediaFilter
import parser.domain.Command
import parser.utils.CommandStream
import source.service.MediaSource

class AssetParserImpl(private val sourceMapper: AssetMapper[MediaSource],
                      private val converterMapper: AssetMapper[AsciiConverter],
                      private val greyscaleMediaFilterMapper: AssetMapper[GreyscaleMediaFilter],
                      private val asciiFilterMapper: AssetMapper[AsciiFilter],
                      private val exporterMapper: AssetMapper[AsciiExporter]) extends AssetParser {

  override def parseArgumentsIntoAssets(commands: Seq[Command]): (MediaSource, AsciiConverter, Seq[GreyscaleMediaFilter], Seq[AsciiFilter], AsciiExporter) = {
    val commandStream = new CommandStream(commands)
    val sources = commandStream.streamThroughMapper(sourceMapper)
    val converters = commandStream.streamThroughMapper(converterMapper)
    val greyscaleMediaFilters = commandStream.streamThroughMapper(greyscaleMediaFilterMapper)
    val asciiFilters = commandStream.streamThroughMapper(asciiFilterMapper)
    val exporters = commandStream.streamThroughMapper(exporterMapper)
    val unknownCommands = commandStream.getCurrent

    if (unknownCommands.nonEmpty)
      throw new IllegalArgumentException(s"Unknown command(s): ${unknownCommands.map(command => s"${command.name}").mkString(", ")}")

    if (sources.length > 1)
      throw new IllegalArgumentException(s"Multiple media source provided. Expected at most one media source.")
    if (converters.length > 1)
      throw new IllegalArgumentException(s"Multiple converters provided. Expected at most one converter.")

    val optionalSource =
      if (sources.isEmpty) sourceMapper.createDefault
      else Some(sources.last)

    if (optionalSource.isEmpty)
      throw new IllegalArgumentException(s"Media source not provided. Expected at least one media source.")

    val optionalConverter =
      if (converters.isEmpty) converterMapper.createDefault
      else Some(converters.last)

    if (optionalConverter.isEmpty)
      throw new IllegalArgumentException(s"Converter not provided. Expected at least one converter.")

    val optionalExporter =
      if (exporters.isEmpty) exporterMapper.createDefault
      else Some(exporters.last)

    if (optionalExporter.isEmpty)
      throw new IllegalArgumentException(s"Exporter not provided. Expected at least one exporter.")

    (optionalSource.get, optionalConverter.get, greyscaleMediaFilters, asciiFilters, optionalExporter.get)
  }
}
