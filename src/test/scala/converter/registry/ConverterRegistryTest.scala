package converter.registry

import converter.service.ascii.AsciiConverter
import converter.service.ascii.linear.LinearAsciiConverter
import converter.service.ascii.nonlinear.DollarDominantAsciiConverter
import converter.service.ascii.linear.PaulBourkesAsciiConverter
import org.scalatest.funsuite.AnyFunSuite
import parser.domain.AssetHandler

class ConverterRegistryTest extends AnyFunSuite {

  private val registry = new ConverterRegistry

  test("\"--table paul-bourkes\" creates PaulBourkesAsciiConverter") {
    val handler = registry.getAssetHandler("table")
    assert(handler.isDefined)
    assert(handler.get.createCallback(Seq("paul-bourkes")).isInstanceOf[PaulBourkesAsciiConverter])
  }

  test("\"--table dollar-dominant\" creates DollarDominantAsciiConverter") {
    val handler = registry.getAssetHandler("table")
    assert(handler.isDefined)
    assert(handler.get.createCallback(Seq("dollar-dominant")).isInstanceOf[DollarDominantAsciiConverter])
  }

  test("\"--table invalid-table\" throws an exception") {
    val invalidTableName = "invalid-table"
    val handler = registry.getAssetHandler("table")
    assert(handler.isDefined)

    val exception = intercept[IllegalArgumentException] {
      handler.get.createCallback(Seq(invalidTableName))
    }

    assert(exception.getMessage.contains(invalidTableName))
  }

  test("\"--custom-table abcd\" creates LinearAsciiConverter with gradient abcd") {
    val gradient = "abdc"
    val handler = registry.getAssetHandler("custom-table")
    assert(handler.isDefined)
    val converter = handler.get.createCallback(Seq(gradient))
    assert(converter.isInstanceOf[LinearAsciiConverter])
    val linearAsciiConverter = converter.asInstanceOf[LinearAsciiConverter]
    assert(linearAsciiConverter.gradient == gradient)
  }

  test("\"--unknown-command\" returns None") {
    val handler = registry.getAssetHandler("unknown-command")
    assert(handler.isEmpty)
  }

  test("createDefault creates PaulBourkesAsciiConverter") {
    val predefined = registry.createDefault
    assert(predefined.isDefined)
    assert(predefined.get.isInstanceOf[PaulBourkesAsciiConverter])
  }
}
