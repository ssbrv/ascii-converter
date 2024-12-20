package filter.registry

import filter.service.ascii.{AsciiFilter, FontAspectRationFilter}
import org.scalatest.funsuite.AnyFunSuite
import parser.domain.AssetHandler

class AsciiFilterRegistryTest extends AnyFunSuite {

  private val registry = new AsciiFilterRegistry

  test("\"--font-aspect-ratio 16:9\" creates FontAspectRationFilter with x=16 and y=9") {
    val handler = registry.getAssetHandler("font-aspect-ratio")
    assert(handler.isDefined)
    val filter = handler.get.createCallback(Seq("16:9"))
    assert(filter.isInstanceOf[FontAspectRationFilter])
    val fontAspectRatioFilter = filter.asInstanceOf[FontAspectRationFilter]
    assert(fontAspectRatioFilter.x == 16)
    assert(fontAspectRatioFilter.y == 9)
  }

  test("\"--font-aspect-ratio invalid\" throws an exception for invalid format") {
    val invalidFormat = "invalid"
    val handler = registry.getAssetHandler("font-aspect-ratio")
    assert(handler.isDefined)

    assertThrows[IllegalArgumentException] {
      handler.get.createCallback(Seq(invalidFormat))
    }
  }

  test("\"--font-aspect-ratio 16:a\" throws a number format exception for y not being a number") {
    val invalidY = "16:a"
    val handler = registry.getAssetHandler("font-aspect-ratio")
    assert(handler.isDefined)

    assertThrows[NumberFormatException] {
      handler.get.createCallback(Seq(invalidY))
    }
  }

  test("\"--font-aspect-ratio a:9\" throws a number format exception for x not being a number") {
    val invalidX = "a:9"
    val handler = registry.getAssetHandler("font-aspect-ratio")
    assert(handler.isDefined)

    assertThrows[NumberFormatException] {
      handler.get.createCallback(Seq(invalidX))
    }
  }

  test("\"--font-aspect-ratio -1:9\" throws an exception for non-positive x value") {
    val negativeX = "-1:9"
    val handler = registry.getAssetHandler("font-aspect-ratio")
    assert(handler.isDefined)

    assertThrows[IllegalArgumentException] {
      handler.get.createCallback(Seq(negativeX))
    }
  }

  test("\"--font-aspect-ratio 0:9\" throws an exception for non-positive zero x value") {
    val zeroX = "-1:9"
    val handler = registry.getAssetHandler("font-aspect-ratio")
    assert(handler.isDefined)

    assertThrows[IllegalArgumentException] {
      handler.get.createCallback(Seq(zeroX))
    }
  }

  test("\"--font-aspect-ratio 16:-1\" throws an exception for non-positive y value") {
    val negativeY = "16:-1"
    val handler = registry.getAssetHandler("font-aspect-ratio")
    assert(handler.isDefined)

    assertThrows[IllegalArgumentException] {
      handler.get.createCallback(Seq(negativeY))
    }
  }

  test("\"--font-aspect-ratio 16:0\" throws an exception for non-positive zero y value") {
    val zeroY = "16:0"
    val handler = registry.getAssetHandler("font-aspect-ratio")
    assert(handler.isDefined)

    assertThrows[IllegalArgumentException] {
      handler.get.createCallback(Seq(zeroY))
    }
  }

  test("\"--unknown-command\" returns None") {
    val handler = registry.getAssetHandler("unknown-command")
    assert(handler.isEmpty)
  }

  test("createDefault returns None") {
    val predefined = registry.createDefault
    assert(predefined.isEmpty)
  }
}
