package service.command

import domain.command.{Command, CommandHandler}

trait CommandService[T] {
  def definedByName(name: String): Boolean
  protected def getDefinitionByName(name: String): CommandHandler[T]
  def parseAll(commands: Seq[Command]): Seq[T]
  def parse(command: Command): T
  def filterDefined(commands: Seq[Command]): Seq[Command]
  def filterUndefined(commands: Seq[Command]): Seq[Command]
}

