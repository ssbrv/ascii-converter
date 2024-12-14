package converter.service.nonlinear

class DollarDominantAsciiConverter extends NonlinearAsciiConverter(
  Seq(
    (200, '$'), // 0-200 -> '$'
    (210, '@'), // 201-210 -> '@'
    (220, 'B'), // 211-220 -> 'B'
    (230, '%'), // 221-230 -> '%'
    (240, '8'), // 231-240 -> '8'
    (245, '&'), // 241-245 -> '&'
    (250, 'M'), // 246-250 -> 'M'
    (255, '.') // 251-255 -> '.'
  )
)
