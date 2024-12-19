package source.mapper

import source.service.importer.{FileImporter, ImageImporter, MultiFramerImporter}

import java.io.File
import java.nio.file.{Files, Path}

class FileImporterMapperImpl extends FileImporterMapper {
  private val definedFormatsForImport: Map[String, File => FileImporter] = Map(
    "image/jpeg" -> createImageImporter,
    "image/png" -> createImageImporter,
    "image/gif" -> createMultiFramerImporter
  )
  private val allowedFileTypes = definedFormatsForImport.map((fileType, _) => fileType).toSeq

  override def map(filePath: String): Option[FileImporter] = {
    val file = new File(filePath)

    if (!file.exists())
      throw new IllegalArgumentException(s"File does not exist: $filePath")

    if (!file.isFile)
      throw new IllegalArgumentException(s"$filePath is not a file")

    if (!file.canRead)
      throw new IllegalArgumentException(s"Cannot read from file: $filePath")

    val mimeType = detectFileType(filePath)
      .getOrElse(throw new IllegalArgumentException(s"Could not determine file type: $filePath"))

    definedFormatsForImport.get(mimeType).map(callback => callback.apply(file))
  }

  override def detectFileType(filePath: String): Option[String] = {
    try {
      Option(Files.probeContentType(Path.of(filePath)))
    } catch {
      case _: Exception => None
    }
  }

  override def getAllowedFileTypes: Seq[String] = allowedFileTypes

  private def createMultiFramerImporter(file: File): MultiFramerImporter = new MultiFramerImporter(file)
  private def createImageImporter(file: File): ImageImporter = new ImageImporter(file)
}
