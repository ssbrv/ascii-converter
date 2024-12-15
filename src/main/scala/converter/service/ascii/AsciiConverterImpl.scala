package converter.service.ascii

import converter.service.MediaConverterImpl

abstract class AsciiConverterImpl extends MediaConverterImpl[Int, Char] with AsciiConverter