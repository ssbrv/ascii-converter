package converter.service.ascii.nonlinear

import common.domain.greyscale.GreyscaleValue

class DollarDominantAsciiConverter extends NonlinearAsciiConverter(
  Seq(
    (GreyscaleValue(200), '$'), // 0-200 -> '$'
    (GreyscaleValue(210), '@'), // 201-210 -> '@'
    (GreyscaleValue(220), 'B'), // 211-220 -> 'B'
    (GreyscaleValue(230), '%'), // 221-230 -> '%'
    (GreyscaleValue(240), '8'), // 231-240 -> '8'
    (GreyscaleValue(245), '&'), // 241-245 -> '&'
    (GreyscaleValue(250), 'M'), // 246-250 -> 'M'
    (GreyscaleValue(255), '.') // 251-255 -> '.'
  )
)
