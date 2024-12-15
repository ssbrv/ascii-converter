package converter.service.ascii.linear

import common.domain.greyscale.GreyscaleValue
import converter.service.MediaConverterImpl
import converter.service.ascii.AsciiConverter

class LinearAsciiConverter(protected val gradient: String) extends MediaConverterImpl[GreyscaleValue, Char] with AsciiConverter {
    
  require(gradient != null && gradient.nonEmpty, "Provided empty gradient for Ascii Linear Converter.")
  require(gradient.length <= 255, "Provided too long gradient for Ascii Linear Converter. It has to be 255 characters at most.")
  
  override protected def convert(greyscale: GreyscaleValue): Char = {
    val index = Math.round((greyscale.value / 255.0) * (gradient.length - 1)).toInt
    gradient.charAt(index)
  }
}
