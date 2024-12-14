package common.utils

import common.mapper.Mapper

import scala.collection.mutable

class MappableStream[T](private val values: Seq[T]) {
  private val valueQueue: mutable.Queue[T] = mutable.Queue.from(values)

  def streamThroughMapper[M](mapper: Mapper[T, M]): Seq[M] = {
    val mappedValues = mutable.Buffer[M]()

    val remaining = mutable.Queue[T]()

    while (valueQueue.nonEmpty) {
      val value = valueQueue.dequeue()
      mapper.map(value) match {
        case Some(mappedElement) =>
          mappedValues += mappedElement
        case None =>
          remaining.enqueue(value)
      }
    }

    valueQueue.enqueueAll(remaining)
    mappedValues.toSeq
  }

  def getCurrent: Seq[T] = valueQueue.toSeq
}
