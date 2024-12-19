package common.mapper

import org.scalatest.funsuite.AnyFunSuite
import parser.domain.{AssetHandler, Command, ParameterCountSet, ParameterCountSingle}
import common.registry.AssetRegistry

class AssetMapperImplTest extends AnyFunSuite {

  class TestAssetRegistry extends AssetRegistry[String] {
    override def getAssetHandler(commandName: String): Option[AssetHandler[String]] = commandName match {
      case "validCommand" =>
        Some(new AssetHandler[String](
          params => params.mkString(", "),
          new ParameterCountSet(Set(1, 2))
        ))
      case "defaultCommand" =>
        Some(new AssetHandler[String](
          _ => "default",
          new ParameterCountSingle(0)
        ))
      case _ => None
    }
  }

  private val testAssetRegistry = new TestAssetRegistry
  private val assetMapper = new AssetMapperImpl(testAssetRegistry)

  test("Correctly maps a valid command") {
    val command = Command("validCommand", Seq("param1", "param2"))
    val result = assetMapper.map(command)
    assert(result.contains("param1, param2"))
  }

  test("Does not map an invalid command") {
    val command = Command("invalidCommand", Seq.empty)
    val result = assetMapper.map(command)
    assert(result.isEmpty)
  }

  test("Throws an exception on parameter count mismatch") {
    val command = Command("validCommand", Seq("param1", "param2", "param3"))
    assertThrows[IllegalArgumentException] {
      assetMapper.map(command)
    }
  }

  test("createDefault by default returns None") {
    val result = assetMapper.createDefault
    assert(result.isEmpty)
  }
}
