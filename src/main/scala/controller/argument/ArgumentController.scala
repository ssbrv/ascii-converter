package controller.argument

import controller.command.*
import domain.command.Command
import service.command.*
import service.argument.ArgumentService
import service.converter.Converter
import service.exporter.Exporter
import service.filter.Filter
import service.importer.Importer

class ArgumentController(argumentService: ArgumentService, 
                         importCommandService: CommandService[Importer],
                         convertCommandService: CommandService[Converter],
                         filterCommandService: CommandService[Filter],
                         exportCommandService: CommandService[Exporter]) {
  private val importCommandController = new ImportCommandController(importCommandService)
  private val convertCommandController = new ConvertCommandController(convertCommandService)
  private val filterCommandController = new FilterCommandController(filterCommandService)
  private val exportCommandController = new ExportCommandController(exportCommandService)

  def parse(arguments: Seq[String]): (Importer, Converter, Seq[Filter], Seq[Exporter]) = {
    val commands: Seq[Command] = argumentService.toCommands(arguments)

    val withoutImportCommands = importCommandController.getLeftOvers(commands)
    val filterAndExportCommands = convertCommandController.getLeftOvers(withoutImportCommands)
    val exportCommands = filterCommandController.getLeftOvers(filterAndExportCommands)
    val unknownCommands = exportCommandController.getLeftOvers(exportCommands)

    if (unknownCommands.nonEmpty)
      throw new IllegalArgumentException(s"Unknown command(s): ${unknownCommands.map(command => s"${command.name}").mkString(", ")}")

    val importer = importCommandController.parse(commands)
    val converter = convertCommandController.parse(withoutImportCommands)
    val filters = filterCommandController.parse(filterAndExportCommands)
    val exporters = exportCommandController.parse(exportCommands)

    (importer, converter, filters, exporters)
  }
}
