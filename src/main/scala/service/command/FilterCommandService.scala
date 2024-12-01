package service.command

import domain.command.CommandHandler
import service.filter.Filter

class FilterCommandService extends CommandServiceImpl[Filter] {
  private val filterCommandHandlers: Map[String, CommandHandler[Filter]] = Map(
    "rotate" -> CommandHandler[Filter](1, 2, params => null),
    "scale" -> CommandHandler[Filter](1, 2, params => null),
    "invert" -> CommandHandler[Filter](1, 2, params => null),
  )

  override def getCommandHandlers: Map[String, CommandHandler[Filter]] = filterCommandHandlers
}
