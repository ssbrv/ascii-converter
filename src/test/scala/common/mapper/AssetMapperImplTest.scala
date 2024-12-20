package common.mapper

import common.registry.AssetRegistry
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.anyString
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.funsuite.AnyFunSuite
import parser.domain.{AssetHandler, Command, ParameterCount}

class AssetMapperImplTest extends AnyFunSuite with MockitoSugar {

  private val mockAssetRegistry = mock[AssetRegistry[String]]
  private val mockParameterCount = mock[ParameterCount]
  private val assetMapper = new AssetMapperImpl(mockAssetRegistry)

  test("map correctly maps a valid command") {
    val command = Command("validCommand", Seq("param1", "param2"))
    val assetHandler = AssetHandler(
      params => params.mkString(", "),
      mockParameterCount
    )
    when(mockAssetRegistry.getAssetHandler(command.name)).thenReturn(Some(assetHandler))
    when(mockParameterCount.isAllowed(command.parameters.length)).thenReturn(true)

    val result = assetMapper.map(command)
    assert(result.contains(command.parameters.mkString(", ")))
  }

  test("map does not map an invalid command") {
    when(mockAssetRegistry.getAssetHandler(anyString())).thenReturn(None)

    val command = Command("invalidCommand", Seq.empty)
    val result = assetMapper.map(command)
    assert(result.isEmpty)
  }

  test("map throws an exception on parameter count mismatch") {
    val command = Command("validCommand", Seq("param1", "param2", "param3"))
    val assetHandler = AssetHandler(
      params => params.mkString(", "),
      mockParameterCount
    )

    when(mockParameterCount.isAllowed(command.parameters.length)).thenReturn(false)
    when(mockParameterCount.getWhatIsAllowed).thenReturn("test")
    when(mockAssetRegistry.getAssetHandler("validCommand")).thenReturn(Some(assetHandler))

    val exception = intercept[IllegalArgumentException] {
      assetMapper.map(command)
    }

    assert(exception.getMessage.contains("test"))
  }

  test("createDefault delegates createDefault to asset registry") {
    when(mockAssetRegistry.createDefault).thenReturn(Some("default-command"))
    val result = assetMapper.createDefault
    assert(result.isDefined)
    assert(result.get == "default-command")
  }
}
