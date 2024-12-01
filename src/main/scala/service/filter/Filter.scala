package service.filter

import domain.frame.ascii.Ascii

trait Filter {
  def filter(ascii: Ascii): Ascii
}
