import common.domain.media.greyscale.GreyscaleMedia
import common.domain.media.pixel.PixelMedia
import converter.service.greyscale.GreyscaleConverter
import parser.service.asset.AssetParser
import parser.service.command.CommandParser

class ApplicationController(private val commandParser: CommandParser, private val assetParser: AssetParser, private val grayscaleConverter: GreyscaleConverter) {
  def run(arguments: Seq[String]): Unit = {
    val commands = commandParser.parseArgumentsIntoCommands(arguments)
    val (source, converter, greyscaleMediaFilters, asciiFilters, exporter) = assetParser.parseArgumentsIntoAssets(commands)

    val pixelMedia: PixelMedia = source.provideMedia()
    var greyscaleMedia: GreyscaleMedia = grayscaleConverter.convert(pixelMedia)
    
    for (filter <- greyscaleMediaFilters)
      greyscaleMedia = filter.apply(greyscaleMedia)
    
    var ascii = converter.convert(greyscaleMedia)
    for (filter <- asciiFilters)
      ascii = filter.apply(ascii)

    exporter.exportMedia(ascii)
  }
}
