package service.command

import domain.command.CommandHandler
import service.converter.Converter

class ConvertCommandService extends CommandServiceImpl[Converter] {
  private val convertCommandHandlers: Map[String, CommandHandler[Converter]] = Map(
    "convert" -> CommandHandler[Converter](1, 2, params => null),
  )

  override def getCommandHandlers: Map[String, CommandHandler[Converter]] = convertCommandHandlers
}
