
import converter.mapper.ConverterMapper
import converter.service.greyscale.{GreyscaleConverter, WeightedMethodGreyscaleConverter}
import exporter.mapper.ExporterMapper
import filter.mapper.{AsciiFilterMapper, GreyscaleMediaFilterMapper}
import parser.service.asset.AssetParserImpl
import parser.service.command.{CommandParser, DoubleDashCommandParser}
import source.mapper.SourceMapper

@main def main(arguments: String*): Unit = {

  val sourceMapper = new SourceMapper
  val converterMapper = new ConverterMapper
  val greyscaleMediaFilterMapper = new GreyscaleMediaFilterMapper
  val asciiFilterMapper = new AsciiFilterMapper
  val exporterMapper = new ExporterMapper

  val assetParser = new AssetParserImpl(
    sourceMapper, converterMapper, greyscaleMediaFilterMapper, asciiFilterMapper, exporterMapper
  )

  val commandParser: CommandParser = new DoubleDashCommandParser
  val greyscaleConverter: GreyscaleConverter = new WeightedMethodGreyscaleConverter

  val applicationController = ApplicationController(
    commandParser, assetParser, greyscaleConverter
  )

  applicationController.run(arguments)
}