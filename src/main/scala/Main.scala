import controller.argument.ArgumentController
import service.argument.DoubleDashArgumentService
import service.command.{ConvertCommandService, ExportCommandService, FilterCommandService, ImportCommandService}

@main def main(arguments: String*): Unit = {
  if (arguments.isEmpty)
    throw new IllegalArgumentException("No arguments provided.")

  val argumentController = new ArgumentController(
    new DoubleDashArgumentService,
    new ImportCommandService,
    new ConvertCommandService,
    new FilterCommandService,
    new ExportCommandService
  )
  val (importer, converter, filters, exporters) = argumentController.parse(arguments)
}