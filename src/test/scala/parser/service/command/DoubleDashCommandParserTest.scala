package parser.service.command

import org.scalatest.funsuite.AnyFunSuite
import parser.domain.Command

class DoubleDashCommandParserTest extends AnyFunSuite {
  private val service = new DoubleDashCommandParser

  def testValidArguments(arguments: Seq[String], expectedCommands: Seq[Command]): Unit = {
    test(s"toCommands(${arguments.mkString(" ")}) succeeds, commands and parameters match") {
      val commands = service.parseArgumentsIntoCommands(arguments)
      assert(commands.length == expectedCommands.length, s"Expected ${expectedCommands.length} commands, but got ${commands.length}")

      commands.zip(expectedCommands).foreach { case (actual, expected) =>
        assert(actual.name == expected.name, s"Expected command name '${expected.name}', but got '${actual.name}'")
        assert(actual.parameters == expected.parameters,
          s"Expected parameters '${expected.parameters.mkString(", ")}' for command '${expected.name}', but got '${actual.parameters.mkString(", ")}'")
      }
    }
  }

  testValidArguments(
    Seq("--cmd1", "param1", "param2", "--cmd2", "param3"),
    Seq(
      Command("cmd1", Seq("param1", "param2")),
      Command("cmd2", Seq("param3"))
    )
  )

  testValidArguments(
    Seq("--cmd1", "--cmd2", "--cmd3"),
    Seq(
      Command("cmd1", Seq()),
      Command("cmd2", Seq()),
      Command("cmd3", Seq()),
    )
  )

  testValidArguments(
    Seq("---cmd1", "----cmd2", "--cmd-3"),
    Seq(
      Command("-cmd1", Seq()),
      Command("--cmd2", Seq()),
      Command("cmd-3", Seq()),
    )
  )

  test("toCommands throws an exception for orphan parameters") {
    val arguments = Seq("param1", "--command1", "param2")

    assertThrows[IllegalArgumentException] {
      service.parseArgumentsIntoCommands(arguments)
    }
  }

  test("toCommands succeeds with no arguments provided") {
    val arguments = Seq.empty[String]
    val commands = service.parseArgumentsIntoCommands(arguments)

    assert(commands.isEmpty)
  }
}
