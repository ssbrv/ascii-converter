package controller.command

import domain.command.Command
import service.command.CommandService
import service.filter.Filter

class FilterCommandController(private val filterCommandService: CommandService[Filter]) {
  def parse(commands: Seq[Command]): Seq[Filter] = filterCommandService.parseAll(filterCommandService.filterDefined(commands))
  
  def getLeftOvers(commands: Seq[Command]): Seq[Command] = filterCommandService.filterUndefined(commands)
}
