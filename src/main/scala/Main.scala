
import common.mapper.AssetMapperImpl
import common.utils.file.FileManagerImpl
import converter.registry.ConverterRegistry
import converter.service.greyscale.WeightedMethodGreyscaleConverter
import exporter.registry.ExporterRegistry
import filter.registry.{AsciiFilterRegistry, GreyscaleMediaFilterRegistry}
import parser.service.asset.AssetParserImpl
import parser.service.command.DoubleDashCommandParser
import source.registry.SourceRegistry

@main def main(arguments: String*): Unit = {
  val fileManager = new FileManagerImpl
  val sourceRegistry = new SourceRegistry(fileManager)
  val converterRegistry = new ConverterRegistry
  val greyscaleMediaFilterRegistry = new GreyscaleMediaFilterRegistry
  val asciiFilterRegistry = new AsciiFilterRegistry
  val exporterRegistry = new ExporterRegistry(fileManager)

  val sourceMapper = new AssetMapperImpl(sourceRegistry)
  val converterMapper = new AssetMapperImpl(converterRegistry)
  val greyscaleMediaFilterMapper = new AssetMapperImpl(greyscaleMediaFilterRegistry)
  val asciiFilterMapper = new AssetMapperImpl(asciiFilterRegistry)
  val exporterMapper = new AssetMapperImpl(exporterRegistry)

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