package service.command

import domain.command.{Command, CommandHandler}

abstract class CommandServiceImpl[T] extends CommandService[T] {
  def parseAll(commands: Seq[Command]): Seq[T] = commands.map(command => parse(command))
  def parse(command: Command): T = {
    val name = command.name
    val parameters = command.parameters

    if (!definedByName(name))
      throw new IllegalArgumentException(s"Unknown command: $name.")

    val commandDefinition = getDefinitionByName(name)

    if (commandDefinition.minNumberOfParameters > parameters.length)
      throw new IllegalArgumentException(
        s"Parameter count mismatch for command: $name. " +
          s"Provided ${parameters.length} parameters: ${parameters.mkString(", ")}. " +
          s"Expected at least ${commandDefinition.minNumberOfParameters} parameters."
      )

    if (commandDefinition.maxNumberOfParameters < parameters.length)
      throw new IllegalArgumentException(
        s"Parameter count mismatch for command: $name. " +
          s"Provided ${parameters.length} parameters: ${parameters.mkString(", ")}. " +
          s"Expected at most ${commandDefinition.maxNumberOfParameters} parameters."
      )

    commandDefinition.construct(parameters)
  }
  def filterDefined(commands: Seq[Command]): Seq[Command] = commands.filter(command => definedByName(command.name))
  def filterUndefined(commands: Seq[Command]): Seq[Command] = commands.filterNot(command => definedByName(command.name))
  override def definedByName(name: String): Boolean = getCommandHandlers.contains(name)
  override protected def getDefinitionByName(name: String): CommandHandler[T] = getCommandHandlers(name)
  
  def getCommandHandlers: Map[String, CommandHandler[T]]
}
