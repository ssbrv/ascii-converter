package controller.command

import domain.command.Command
import service.command.CommandService
import service.exporter.Exporter

class ExportCommandController(private val exportCommandService: CommandService[Exporter]) {
  def parse(commands: Seq[Command]): Seq[Exporter] = {
    val definedExportCommands = exportCommandService.filterDefined(commands)

    if (definedExportCommands.isEmpty)
      throw new IllegalArgumentException(s"Missing export command.")
    
    if (definedExportCommands.length > 5)
      throw new IllegalArgumentException(s"Provided ${definedExportCommands.length} export commands: ${commands.mkString(", ")}. Expected at most 5 export commands.")

    exportCommandService.parseAll(definedExportCommands)
  }
  
  def getLeftOvers(commands: Seq[Command]): Seq[Command] = exportCommandService.filterUndefined(commands) 
}
