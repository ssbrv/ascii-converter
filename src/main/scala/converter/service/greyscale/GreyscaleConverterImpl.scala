package converter.service.greyscale

import common.domain.pixel.Pixel
import converter.service.MediaConverterImpl

class GreyscaleConverterImpl extends MediaConverterImpl[Pixel, Int] with GreyscaleConverter {
  override protected def convert(value: Pixel): Int = 
    (0.3 * value.red + 0.59 * value.green + 0.11 * value.blue).toInt
}