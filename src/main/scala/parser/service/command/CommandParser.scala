package parser.service.command

import parser.domain.Command

trait CommandParser {
  def parseArgumentsIntoCommands(arguments: Seq[String]): Seq[Command]
}
