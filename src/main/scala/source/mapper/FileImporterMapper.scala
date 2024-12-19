package source.mapper

import common.mapper.Mapper
import source.service.importer.FileImporter

trait FileImporterMapper extends Mapper[String, FileImporter] {
  override def map(filePath: String): Option[FileImporter]
  def detectFileType(filePath: String): Option[String]
  def getAllowedFileTypes: Seq[String]
}
