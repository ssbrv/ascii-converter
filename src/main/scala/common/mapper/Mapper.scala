package common.mapper

trait Mapper[M, T] {
  def map(value: M): Option[T]
}

