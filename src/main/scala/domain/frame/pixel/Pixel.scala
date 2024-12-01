package domain.frame.pixel

import Pixel.{rgbToBlue, rgbToGreen, rgbToRed}

class Pixel(
  private val red: Int,
  private val green: Int,
  private val blue: Int) {
  
  def this(rgb: Int) = this(rgbToRed(rgb), rgbToGreen(rgb), rgbToBlue(rgb))

  require(red >= 0 && red <= 255, "Red value must be between 0 and 255")
  require(green >= 0 && green <= 255, "Green value must be between 0 and 255")
  require(blue >= 0 && blue <= 255, "Blue value must be between 0 and 255")

  private val greyscale: Int =
    (0.3 * red + 0.59 * green + 0.11 * blue).toInt

  def getGreyscale: Int = greyscale
  
  def getRed: Int = red
  def getGreen: Int = green
  def getBlue: Int = blue
}

object Pixel {
  def rgbToRed(rgb: Int): Int = (rgb >> 16) & 0xFF
  def rgbToGreen(rgb: Int): Int = (rgb >> 8) & 0xFF
  def rgbToBlue(rgb: Int): Int = rgb & 0xFF
}