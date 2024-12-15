package converter.service.ascii.nonlinear

import converter.service.ascii.AsciiConverterImpl

class NonlinearAsciiConverter(protected val gradientTable: Seq[(Int, Char)]) extends AsciiConverterImpl {
  override protected def convert(greyscale: Int): Char = {
    gradientTable.collectFirst {
      case (threshold, char) if greyscale <= threshold => char
    }.getOrElse('.')
  }
}
