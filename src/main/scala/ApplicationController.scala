import parser.service.asset.AssetParser
import parser.service.command.CommandParser

class ApplicationController(private val commandParser: CommandParser, private val assetParser: AssetParser) {
  def run(arguments: Seq[String]): Unit = {
    val commands = commandParser.parseArgumentsIntoCommands(arguments)
    val (importer, converter, filters, exporter) = assetParser.parseArgumentsIntoAssets(commands)

    val media = importer.provideMedia()
    var ascii = converter.convertToAscii(media)
    for (filter <- filters)
      ascii = filter.apply(ascii)

    exporter.exportAscii(ascii)
  }
}
