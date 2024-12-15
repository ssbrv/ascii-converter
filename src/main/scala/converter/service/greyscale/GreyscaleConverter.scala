package converter.service.greyscale

import common.domain.pixel.Pixel
import converter.service.MediaConverter

trait GreyscaleConverter extends MediaConverter[Pixel, Int]
