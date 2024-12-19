package common.mapper

trait Mapper[F, T] {
  def map(value: F): Option[T]
}

