package filter.ascii.service

import common.domain.frame.ascii.Ascii

trait AsciiFilter {
  def apply(ascii: Ascii): Ascii
}
