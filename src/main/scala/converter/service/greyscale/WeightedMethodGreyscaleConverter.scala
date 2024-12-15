package converter.service.greyscale

import common.domain.greyscale.GreyscaleValue
import common.domain.pixel.Pixel
import converter.service.MediaConverterImpl

class WeightedMethodGreyscaleConverter extends MediaConverterImpl[Pixel, GreyscaleValue] with GreyscaleConverter {
  override protected def convert(pixel: Pixel): GreyscaleValue =
    GreyscaleValue((0.3 * pixel.red + 0.59 * pixel.green + 0.11 * pixel.blue).toInt)
}
