package converter.service.ascii.linear

import common.domain.greyscale.GreyscaleValue
import converter.service.MediaConverterImpl
import converter.service.ascii.AsciiConverter

class LinearAsciiConverter(val gradient: String) extends MediaConverterImpl[GreyscaleValue, Char] with AsciiConverter {
    
  require(gradient != null && gradient.nonEmpty, "Provided empty gradient for Ascii Linear Converter.")
  require(gradient.length <= 256, s"Provided too long gradient of length ${gradient.length} for Ascii Linear Converter. It has to be 256 characters at most.")
  private val step = 256.0 / gradient.length

  override def convert(greyscale: GreyscaleValue): Char = {
    val index = greyscale.value / step
    gradient.charAt(index.toInt)
  }
}