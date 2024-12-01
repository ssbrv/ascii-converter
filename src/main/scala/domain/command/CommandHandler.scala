package domain.command

case class CommandHandler[T](minNumberOfParameters: Integer, maxNumberOfParameters: Integer, construct: Seq[String] => T)
