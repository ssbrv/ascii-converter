package source.mapper.importer

import source.service.importer.{FileImporter, ImageImporter, MultiFramerImporter}

import java.io.File
import java.nio.file.Files

class FileImporterMapperImpl extends FileImporterMapper {
  private val definedFormatsForImport: Map[String, File => FileImporter] = Map(
    "image/jpeg" -> createImageImporter,
    "image/png" -> createImageImporter,
    "image/gif" -> createMultiFramerImporter
  )

  override def map(filePath: String): FileImporter = {
    val file = new File(filePath)

    if (!file.exists())
      throw new IllegalArgumentException(s"File does not exist: $filePath")

    if (!file.isFile)
      throw new IllegalArgumentException(s"$filePath is not a file")

    if (!file.canRead)
      throw new IllegalArgumentException(s"Cannot read from file: $filePath")

    val mimeType = detectMimeType(file)
      .getOrElse(throw new IllegalArgumentException(s"Could not determine file type: $filePath"))

    definedFormatsForImport
      .getOrElse(
        mimeType,
        throw new IllegalArgumentException(s"Unsupported file type: $mimeType")
      )(file)
  }

  private def detectMimeType(file: File): Option[String] = {
    try {
      Option(Files.probeContentType(file.toPath))
    } catch {
      case _: Exception => None
    }
  }

  private def createMultiFramerImporter(file: File): MultiFramerImporter = new MultiFramerImporter(file)
  private def createImageImporter(file: File): ImageImporter = new ImageImporter(file)
}
