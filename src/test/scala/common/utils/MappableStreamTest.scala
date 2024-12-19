package common.utils

import common.mapper.Mapper
import org.scalatest.funsuite.AnyFunSuite

class MappableStreamTest extends AnyFunSuite {

  private val positiveMapper = new Mapper[Int, String] {
    override def map(value: Int): Option[String] = {
      if (value <= 0)
        return None

      Some(value.toString)
    }
  }

  private val negativeMapper = new Mapper[Int, String] {
    override def map(value: Int): Option[String] = {
      if (value >= 0)
        return None

      Some(value.toString)
    }
  }

  def assertStreamMapper[F, T](mapper: Mapper[F, T], values: Seq[F], expectedResult: Seq[T], expectedLeftover: Seq[F]): MappableStream[F] = {
    val stream = new MappableStream(values)
    assertStreamMapper(mapper, expectedResult, expectedLeftover, stream)
  }

  def assertStreamMapper[F, T](mapper: Mapper[F, T], expectedResult: Seq[T], expectedLeftover: Seq[F], stream: MappableStream[F]): MappableStream[F] = {
    val result = stream.streamThroughMapper(mapper)
    assert(result == expectedResult)
    assert(stream.getCurrent == expectedLeftover)
    stream
  }

  test("streamThroughMapper correctly maps values in correct order and returns correct leftover in correct order") {
    assertStreamMapper(
      positiveMapper,
      Seq(-5, 10, -5, -1, 1, -4, 1, 5),
      Seq("10", "1", "1", "5"),
      Seq(-5, -5, -1, -4)
    )
  }

  test("streamThroughMapper correctly handles empty input") {
    assertStreamMapper(
      positiveMapper,
      Seq(),
      Seq(),
      Seq()
    )
  }

  test("streamThroughMapper correctly handles all to map situation") {
    assertStreamMapper(
      positiveMapper,
      Seq(1, 2, 3, 4, 5),
      Seq("1", "2", "3", "4", "5"),
      Seq()
    )
  }

  test("streamThroughMapper correctly handles none to map situation") {
    assertStreamMapper(
      positiveMapper,
      Seq(-1, -2, -3, -4, -5),
      Seq(),
      Seq(-1, -2, -3, -4, -5),
    )
  }

  test("streamThroughMapper correctly handles two mappers") {
    val stream = assertStreamMapper(
      positiveMapper,
      Seq(-5, 10, -5, 0, -1, 1, -4, 1, 5),
      Seq("10", "1", "1", "5"),
      Seq(-5, -5, 0,-1, -4)
    )

    assertStreamMapper(
      negativeMapper,
      Seq("-5", "-5", "-1", "-4"),
      Seq(0),
      stream
    )
  }
}
