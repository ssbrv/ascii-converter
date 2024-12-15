package converter.service.ascii

import common.domain.greyscale.GreyscaleValue
import converter.service.MediaConverter

type AsciiConverter = MediaConverter[GreyscaleValue, Char]
