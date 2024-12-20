package converter.service.ascii.nonlinear

import common.domain.greyscale.GreyscaleValue
import converter.service.MediaConverterImpl
import converter.service.ascii.AsciiConverter

class NonlinearAsciiConverter(protected val gradientTable: Seq[(GreyscaleValue, Char)]) extends MediaConverterImpl[GreyscaleValue, Char] with AsciiConverter {

  require(gradientTable.nonEmpty, "Provided empty gradient table for Ascii Non Linear Converter.")
  require(gradientTable.exists(_._1.value == 255), "The gradient table must contain a mapping for all values (there has to be a record (255, char)).")

  override def convert(greyscale: GreyscaleValue): Char = {
    gradientTable.collectFirst {
      case (threshold, char) if greyscale.value <= threshold.value => char
    }.get
  }
}
