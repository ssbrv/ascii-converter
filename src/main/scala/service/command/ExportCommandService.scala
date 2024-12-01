package service.command

import domain.command.CommandHandler
import service.exporter.Exporter

class ExportCommandService extends CommandServiceImpl[Exporter] {
  private val exportCommandHandlers: Map[String, CommandHandler[Exporter]] = Map(
    "save" -> CommandHandler[Exporter](1, 2, params => null),
    "print" -> CommandHandler[Exporter](1, 2, params => null),
  )

  override def getCommandHandlers: Map[String, CommandHandler[Exporter]] = exportCommandHandlers
}
