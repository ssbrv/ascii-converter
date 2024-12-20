package common.utils.file

import java.io.File
import java.nio.file.{Files, Path}

class FileManagerImpl extends FileManager {
  override def detectFileType(file: File): Option[String] = {
    try {
      val mimeType = Files.probeContentType(Path.of(file.getPath))

      mimeType match {
        case null => None
        case mime if mime.startsWith("image/") =>
          val extension = mime.split("/")(1)
          Some(extension)
        case mime if mime.startsWith("text/") =>
          Some("txt")
        case mime => Some(mime)
      }

    } catch {
      case _: Exception => None
    }
  }

  override def exists(file: File): Boolean = file.exists()

  override def isFile(file: File): Boolean = file.isFile

  override def canRead(file: File): Boolean = file.canRead

  override def canWrite(file: File): Boolean = file.canRead
}
