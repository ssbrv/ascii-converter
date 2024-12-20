package exporter.registry

import common.registry.AssetRegistry
import common.utils.file.FileManager
import exporter.service.ascii.{AsciiConsolePrinter, AsciiExporter, AsciiFileExporter}
import parser.domain.{AssetHandler, ParameterCountSingle}

import java.io.File

class ExporterRegistry(private val fileTypeDetector: FileManager) extends AssetRegistry[AsciiExporter] {
  private val exporterHandlers: Map[String, AssetHandler[AsciiExporter]] = Map(
    "output-file" -> AssetHandler[AsciiExporter](createFileExporter, new ParameterCountSingle(1)),
    "output-console" -> AssetHandler[AsciiExporter](_ => new AsciiConsolePrinter),
  )

  private def createFileExporter(params: Seq[String]): AsciiFileExporter = new AsciiFileExporter(new File(params.last), fileTypeDetector)

  override def getAssetHandler(commandName: String): Option[AssetHandler[AsciiExporter]] = exporterHandlers.get(commandName)

  override def createDefault: Option[AsciiExporter] = Some(new AsciiConsolePrinter)
}
