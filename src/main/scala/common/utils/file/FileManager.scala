package common.utils.file

import java.io.File

trait FileManager {
  def exists(file: File): Boolean
  def isFile(file: File): Boolean
  def canRead(file: File): Boolean
  def canWrite(file: File): Boolean
  def detectFileType(file: File): Option[String]
}
