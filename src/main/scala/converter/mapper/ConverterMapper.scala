package converter.mapper

import common.mapper.AssetMapper
import converter.service.ascii.AsciiConverter
import converter.service.ascii.linear.{LinearAsciiConverter, PaulBourkesAsciiConverter}
import converter.service.ascii.nonlinear.DollarDominantAsciiConverter
import parser.domain.AssetHandler

class ConverterMapper extends AssetMapper[AsciiConverter] {
  private val converterHandlers: Map[String, AssetHandler[AsciiConverter]] = Map(
    "table" -> AssetHandler[AsciiConverter](params => createPredefined(params.last), Set(1)),
    "custom-table" -> AssetHandler[AsciiConverter](params => createCustomLinear(params.last), Set(1)),
  )

  private val predefined: Map[String, () => AsciiConverter] = Map(
    "paul-bourkes" -> (() => new PaulBourkesAsciiConverter),
    "dollar-dominant" -> (() => new DollarDominantAsciiConverter)
  )

  override protected def getAssetHandler(commandName: String): Option[AssetHandler[AsciiConverter]] = converterHandlers.get(commandName)

  private def createPredefined(name: String): AsciiConverter = {
    predefined.getOrElse(
      name,
      throw new IllegalArgumentException(s"Unknown predefined Ascii Converter: $name. Predefined: ${predefined.keys.mkString(", ")}")
    )()
  }

  private def createCustomLinear(gradient: String): LinearAsciiConverter = new LinearAsciiConverter(gradient)

  override def createDefault: Option[AsciiConverter] = Some(new PaulBourkesAsciiConverter)
}
