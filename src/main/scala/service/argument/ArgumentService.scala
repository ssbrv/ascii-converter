package service.argument

import domain.command.Command

trait ArgumentService {
  def toCommands(arguments: Seq[String]): Seq[Command]
}
