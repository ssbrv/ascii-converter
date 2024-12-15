package converter.service.ascii.nonlinear

import common.domain.greyscale.GreyscaleValue
import converter.service.MediaConverterImpl
import converter.service.ascii.AsciiConverter

class NonlinearAsciiConverter(protected val gradientTable: Seq[(Int, Char)]) extends MediaConverterImpl[GreyscaleValue, Char] with AsciiConverter {
  override protected def convert(greyscale: GreyscaleValue): Char = {
    gradientTable.collectFirst {
      case (threshold, char) if greyscale.value <= threshold => char
    }.getOrElse('.')
  }
}
