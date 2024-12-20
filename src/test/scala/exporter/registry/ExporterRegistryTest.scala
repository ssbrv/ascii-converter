package exporter.registry

import common.utils.file.FileManager
import exporter.service.ascii.{AsciiConsolePrinter, AsciiExporter, AsciiFileExporter}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.mockito.MockitoSugar
import parser.domain.AssetHandler

import java.io.File

class ExporterRegistryTest extends AnyFunSuite with MockitoSugar {

  private val mockFileTypeDetector = mock[FileManager]
  private val registry = new ExporterRegistry(mockFileTypeDetector)

  test("\"--output-file filename.txt\" creates AsciiFileExporter") {
    val handler = registry.getAssetHandler("output-file")
    assert(handler.isDefined)
    val exporter = handler.get.createCallback(Seq("filename.txt"))
    assert(exporter.isInstanceOf[AsciiFileExporter])
    val fileExporter = exporter.asInstanceOf[AsciiFileExporter]
    assert(fileExporter.file == new File("filename.txt"))
    assert(fileExporter.fileManager == mockFileTypeDetector)
  }

  test("\"--output-console\" creates AsciiConsolePrinter") {
    val handler = registry.getAssetHandler("output-console")
    assert(handler.isDefined)
    val exporter = handler.get.createCallback(Seq.empty)
    assert(exporter.isInstanceOf[AsciiConsolePrinter])
  }

  test("\"--unknown-command\" returns None") {
    val handler = registry.getAssetHandler("unknown-command")
    assert(handler.isEmpty)
  }

  test("createDefault creates AsciiConsolePrinter") {
    val defaultExporter = registry.createDefault
    assert(defaultExporter.isDefined)
    assert(defaultExporter.get.isInstanceOf[AsciiConsolePrinter])
  }
}
