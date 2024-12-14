package converter.service

import common.domain.frame.ascii.Ascii
import common.domain.frame.pixel.media.Media

trait AsciiConverter {
  def convertToAscii(media: Media): Ascii
}
