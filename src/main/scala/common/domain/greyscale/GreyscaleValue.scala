package common.domain.greyscale

import common.domain.greyscale.GreyscaleValue.{MAX_VALUE, MIN_VALUE}

case class GreyscaleValue(value: Int) {
  require(value >= MIN_VALUE && value <= MAX_VALUE, s"Invalid greyscale value: $value")
}

object GreyscaleValue {
  val MIN_VALUE = 0
  val MAX_VALUE = 255
}