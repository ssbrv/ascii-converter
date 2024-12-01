package controller.command

import domain.command.Command
import service.command.CommandService
import service.importer.Importer

class ImportCommandController(private val importCommandService: CommandService[Importer]) {
  def parse(commands: Seq[Command]): Importer = {
    val definedImportCommands = importCommandService.filterDefined(commands)

    if (definedImportCommands.isEmpty)
      throw new IllegalArgumentException(s"Missing import command.")

    if (definedImportCommands.length > 1)
      throw new IllegalArgumentException(s"Provided multiple import commands: ${commands.mkString(", ")}. Expected only one import command.")

    importCommandService.parse(definedImportCommands.last)
  }
  
  def getLeftOvers(commands: Seq[Command]): Seq[Command] = importCommandService.filterUndefined(commands)
}
