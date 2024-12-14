
import converter.mapper.ConverterMapper
import exporter.mapper.ExporterMapper
import filter.ascii.mapper.AsciiFilterMapper
import parser.service.asset.AssetParserImpl
import parser.service.command.{CommandParser, DoubleDashCommandParser}
import source.mapper.SourceMapper

@main def main(arguments: String*): Unit = {

  val sourceMapper = new SourceMapper
  val converterMapper = new ConverterMapper
  val asciiFilterMapper = new AsciiFilterMapper
  val exporterMapper = new ExporterMapper

  val assetParser = new AssetParserImpl(
    sourceMapper, converterMapper, asciiFilterMapper, exporterMapper
  )

  val commandParser: CommandParser = new DoubleDashCommandParser

  val applicationController = ApplicationController(
    commandParser, assetParser
  )

  applicationController.run(arguments)
}