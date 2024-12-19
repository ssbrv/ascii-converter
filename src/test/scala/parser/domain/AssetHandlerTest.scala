package parser.domain

import org.scalatest.funsuite.AnyFunSuite

class AssetHandlerTest extends AnyFunSuite {
  test("Defaults to allowing only 0 parameter count") {
    val assetHandler = new AssetHandler[Int](_ => 1)
    assert(assetHandler.allowedParamCount.isAllowed(0))
    assert(!assetHandler.allowedParamCount.isAllowed(1))
  }
}
