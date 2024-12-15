package converter.service.greyscale

import common.domain.greyscale.GreyscaleValue
import common.domain.pixel.Pixel
import converter.service.MediaConverter

type GreyscaleConverter = MediaConverter[Pixel, GreyscaleValue]