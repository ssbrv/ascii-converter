package common.domain.pixel

import java.awt.Color

case class Pixel(color: Color) {
  def this(rgb: Int) = this(new Color(rgb))
  def this(red: Int, green: Int, blue: Int) = this(new Color(red, green, blue))

  val red: Int = color.getRed
  val green: Int = color.getGreen
  val blue: Int = color.getBlue
}