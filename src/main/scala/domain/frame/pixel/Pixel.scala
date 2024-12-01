package domain.frame.pixel

import java.awt.Color

class Pixel(private val color: Color) {
  def this(rgb: Int) = this(new Color(rgb))
  def this(red: Int, green: Int, blue: Int) = this(new Color(red, green, blue))

  val red: Int = color.getRed
  val green: Int = color.getGreen
  val blue: Int = color.getBlue
  
  val greyscale: Int = (0.3 * red + 0.59 * green + 0.11 * blue).toInt
}