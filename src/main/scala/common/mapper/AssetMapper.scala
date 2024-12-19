package common.mapper

import parser.domain.Command

trait AssetMapper[T] extends Mapper[Command, T] {
  override def map(command: Command): Option[T]
  def createDefault: Option[T]
}
