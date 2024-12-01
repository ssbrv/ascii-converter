package controller.command

import domain.command.Command
import service.command.CommandService
import service.converter.Converter

class ConvertCommandController(private val convertCommandService: CommandService[Converter]) {
  def parse(commands: Seq[Command]): Converter = {
    val definedConvertCommands = convertCommandService.filterDefined(commands)

    if (definedConvertCommands.isEmpty)
      throw new IllegalArgumentException(s"Missing convert command.")

    if (definedConvertCommands.length > 1)
      throw new IllegalArgumentException(s"Provided multiple convert commands: ${commands.mkString(", ")}. Expected only one convert command.")

    convertCommandService.parse(definedConvertCommands.last)
  }

  def getLeftOvers(commands: Seq[Command]): Seq[Command] = convertCommandService.filterUndefined(commands)
}
