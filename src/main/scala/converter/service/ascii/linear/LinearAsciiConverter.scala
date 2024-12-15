package converter.service.ascii.linear

import converter.service.ascii.AsciiConverterImpl

class LinearAsciiConverter(protected val gradient: String) extends AsciiConverterImpl {
    
  require(gradient != null && gradient.nonEmpty, "Provided empty gradient for Ascii Linear Converter.")
  require(gradient.length <= 255, "Provided too long gradient for Ascii Linear Converter. It has to be 255 characters at most.")
  
  override protected def convert(greyscale: Int): Char = {
    val index = Math.round((greyscale / 255.0) * (gradient.length - 1)).toInt
    gradient.charAt(index)
  }
}
