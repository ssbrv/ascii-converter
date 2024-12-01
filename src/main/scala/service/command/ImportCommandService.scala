package service.command

import domain.command.CommandHandler
import service.importer.Importer

class ImportCommandService extends CommandServiceImpl[Importer] {
  private val importCommandHandlers: Map[String, CommandHandler[Importer]] = Map(
    "import" -> CommandHandler[Importer](1, 2, params => null),
    "generate" -> CommandHandler[Importer](1, 2, params => null),
    "download" -> CommandHandler[Importer](1, 2, params => null),
  )

  override def getCommandHandlers: Map[String, CommandHandler[Importer]] = importCommandHandlers
}
