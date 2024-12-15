package source.mapper.importer

import source.mapper.SourceMapper
import source.service.importer.FileImporter

trait FileImporterMapper {
  def map(filePath: String): FileImporter
}
