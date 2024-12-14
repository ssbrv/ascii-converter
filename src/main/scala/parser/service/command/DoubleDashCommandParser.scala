package parser.service.command

import parser.domain.Command

class DoubleDashCommandParser extends CommandParser {
  def parseArgumentsIntoCommands(arguments: Seq[String]): Seq[Command] = arguments.foldLeft(Seq.empty[Command]) { (commands, argument) =>
    if (argument.startsWith("--"))
      commands :+ Command(argument.substring(2), Seq.empty)
    else if (commands.isEmpty)
      throw new IllegalArgumentException(s"Orphan parameter: $argument.")
    else commands.init :+ Command(commands.last.name, commands.last.parameters :+ argument)
  }
}
