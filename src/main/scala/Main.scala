
import converter.mapper.ConverterMapper
import converter.service.greyscale.WeightedMethodGreyscaleConverter
import exporter.mapper.ExporterMapper
import filter.mapper.{AsciiFilterMapper, GreyscaleMediaFilterMapper}
import parser.service.asset.AssetParserImpl
import parser.service.command.DoubleDashCommandParser
import source.mapper.SourceMapper
import source.mapper.importer.FileImporterMapperImpl

@main def main(arguments: String*): Unit = {
  val fileImporterMapper = new FileImporterMapperImpl
  val sourceMapper = new SourceMapper(fileImporterMapper)
  val converterMapper = new ConverterMapper
  val greyscaleMediaFilterMapper = new GreyscaleMediaFilterMapper
  val asciiFilterMapper = new AsciiFilterMapper
  val exporterMapper = new ExporterMapper

  val assetParser = new AssetParserImpl(
    sourceMapper, converterMapper, greyscaleMediaFilterMapper, asciiFilterMapper, exporterMapper
  )

  val commandParser = new DoubleDashCommandParser
  val greyscaleConverter = new WeightedMethodGreyscaleConverter

  val applicationController = ApplicationController(
    commandParser, assetParser, greyscaleConverter
  )

  applicationController.run(arguments)
}