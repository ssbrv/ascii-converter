package parser.domain

case class AssetHandler[T](createCallback: Seq[String] => T, allowedParamCount: ParameterCount = new ParameterCountSingle)