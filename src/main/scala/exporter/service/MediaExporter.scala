package exporter.service

import common.domain.media.Media

trait MediaExporter[T] {
  def exportMedia(media: Media[T]): Unit
}
