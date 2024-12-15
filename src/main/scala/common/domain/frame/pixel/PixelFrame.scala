package common.domain.frame.pixel

import common.domain.frame.Frame
import common.domain.pixel.Pixel

class PixelFrame(pixels: Seq[Seq[Pixel]]) extends Frame[Pixel](pixels)
