package exporter.mapper

import common.mapper.AssetMapper
import exporter.service.{AsciiExporter, ConsolePrinter, FileExporter}
import parser.domain.AssetHandler

import java.io.File

class ExporterMapper extends AssetMapper[AsciiExporter] {
  private val exporterHandlers: Map[String, AssetHandler[AsciiExporter]] = Map(
    "output-file" -> AssetHandler[AsciiExporter](createFileExporter, Set(1)),
    "output-console" -> AssetHandler[AsciiExporter](_ => new ConsolePrinter),
  )

  private def createFileExporter(params: Seq[String]): FileExporter = new FileExporter(new File(params.last))

  override protected def getAssetHandler(commandName: String): Option[AssetHandler[AsciiExporter]] = exporterHandlers.get(commandName)

  override def createDefault: Option[AsciiExporter] = Some(new ConsolePrinter)
}
