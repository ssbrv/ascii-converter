package filter.registry

import filter.service.greyscale.{BrightnessFilter, GreyscaleMediaFilter, InvertFilter}
import org.scalatest.funsuite.AnyFunSuite
import parser.domain.AssetHandler

class GreyscaleMediaFilterRegistryTest extends AnyFunSuite {

  private val registry = new GreyscaleMediaFilterRegistry

  test("\"--brightness 50\" creates BrightnessFilter with delta=50") {
    val handler = registry.getAssetHandler("brightness")
    assert(handler.isDefined)
    val filter = handler.get.createCallback(Seq("50"))
    assert(filter.isInstanceOf[BrightnessFilter])
    val brightnessFilter = filter.asInstanceOf[BrightnessFilter]
    assert(brightnessFilter.brightnessDelta == 50)
  }

  test("\"--brightness invalid\" throws NumberFormatException for non-numeric brightness") {
    val handler = registry.getAssetHandler("brightness")
    assert(handler.isDefined)
    assertThrows[NumberFormatException] {
      handler.get.createCallback(Seq("invalid"))
    }
  }

  test("\"--invert\" creates InvertFilter") {
    val handler = registry.getAssetHandler("invert")
    assert(handler.isDefined)
    val filter = handler.get.createCallback(Seq.empty)
    assert(filter.isInstanceOf[InvertFilter])
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
