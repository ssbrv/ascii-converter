package source.registry

import common.utils.file.FileManager
import source.service.importer.{FileImporter, ImageImporter, MultiFramerImporter}
import source.service.random.{RandomImageGenerator, SmallImageGenerator}
import parser.domain.AssetHandler
import org.mockito.Mockito.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.mockito.MockitoSugar

import java.io.File

class SourceRegistryTest extends AnyFunSuite with MockitoSugar {

  private val mockFileManager = mock[FileManager]
  private val registry = new SourceRegistry(mockFileManager)
  private val imageHandler = registry.getAssetHandler("image")
  private val imageRandomHandler = registry.getAssetHandler("image-random")
  private val filePath = "filePath"
  private val file = new File(filePath)

  private def setMocks(fileType: String): Unit = {
    when(mockFileManager.detectFileType(file)).thenReturn(Some(fileType))
    when(mockFileManager.exists(file)).thenReturn(true)
    when(mockFileManager.isFile(file)).thenReturn(true)
    when(mockFileManager.canRead(file)).thenReturn(true)
  }

  test("--image is defined") {
    assert(imageHandler.isDefined)
  }

  test("--image creates ImageImporter for png") {
    setMocks("png")

    val importer = imageHandler.get.createCallback(Seq(filePath))
    assert(importer.isInstanceOf[FileImporter])
    val imageImporter = importer.asInstanceOf[ImageImporter]
    assert(imageImporter.file == file)
  }

  test("--image creates ImageImporter for jpg") {
    setMocks("jpg")

    val importer = imageHandler.get.createCallback(Seq(filePath))
    assert(importer.isInstanceOf[FileImporter])
    val imageImporter = importer.asInstanceOf[ImageImporter]
    assert(imageImporter.file == file)
  }

  test("--image creates ImageImporter for jpeg") {
    setMocks("jpeg")

    val importer = imageHandler.get.createCallback(Seq(filePath))
    assert(importer.isInstanceOf[FileImporter])
    val imageImporter = importer.asInstanceOf[ImageImporter]
    assert(imageImporter.file == file)
  }

  test("--image creates MultiFramerImporter for gif") {
    setMocks("gif")

    val importer = imageHandler.get.createCallback(Seq(filePath))
    assert(importer.isInstanceOf[MultiFramerImporter])
    val multiFramerImporter = importer.asInstanceOf[MultiFramerImporter]
    assert(multiFramerImporter.file == file)
  }

  test("--image throws exception for non existing file") {
    when(mockFileManager.exists(file)).thenReturn(false)
    assertThrows[IllegalArgumentException] {
      imageHandler.get.createCallback(Seq(filePath))
    }
  }

  test("--image throws exception for non file") {
    when(mockFileManager.exists(file)).thenReturn(true)
    when(mockFileManager.exists(file)).thenReturn(false)
    assertThrows[IllegalArgumentException] {
      imageHandler.get.createCallback(Seq(filePath))
    }
  }

  test("--image throws exception for cannot read file") {
    when(mockFileManager.exists(file)).thenReturn(true)
    when(mockFileManager.exists(file)).thenReturn(true)
    when(mockFileManager.canRead(file)).thenReturn(false)
    assertThrows[IllegalArgumentException] {
      imageHandler.get.createCallback(Seq(filePath))
    }
  }

  test("--image-random is defined") {
    assert(imageRandomHandler.isDefined)
  }

  test("\"--image-random 100 200\" creates RandomImageGenerator with ranges x:100-100 and y:200-200") {
    val x = "100"
    val y = "200"

    val generator = imageRandomHandler.get.createCallback(Seq(x, y))
    assert(generator.isInstanceOf[RandomImageGenerator])
    val randomGenerator = generator.asInstanceOf[RandomImageGenerator]
    assert(randomGenerator.minWidth == x.toInt)
    assert(randomGenerator.maxWidth == x.toInt)
    assert(randomGenerator.minHeight == y.toInt)
    assert(randomGenerator.maxHeight == y.toInt)
  }

  test("\"--image-random 100 a\" throws number format exception as y in not a number") {
    val x = "100"
    val y = "a"

    assertThrows[NumberFormatException] {
      imageRandomHandler.get.createCallback(Seq(x, y))
    }
  }

  test("\"--image-random a 200\" throws number format exception as x in not a number") {
    val x = "a"
    val y = "200"

    assertThrows[NumberFormatException] {
      imageRandomHandler.get.createCallback(Seq(x, y))
    }
  }

  test("\"--image-random -100 200\" throws exception as x is a negative number") {
    val x = "-100"
    val y = "200"

    assertThrows[IllegalArgumentException] {
      imageRandomHandler.get.createCallback(Seq(x, y))
    }
  }

  test("\"--image-random 100 -200\" throws exception as y is a negative number") {
    val x = "100"
    val y = "-200"

    assertThrows[IllegalArgumentException] {
      imageRandomHandler.get.createCallback(Seq(x, y))
    }
  }

  test("\"--image-random\" creates SmallImageGenerator") {
    val generator = imageRandomHandler.get.createCallback(Seq())
    assert(generator.isInstanceOf[SmallImageGenerator])
    val randomGenerator = generator.asInstanceOf[SmallImageGenerator]
  }

  test("\"unknown-command\") returns None") {
    val handler = registry.getAssetHandler("unknown-command")
    assert(handler.isEmpty)
  }

  test("createDefault returns None") {
    val default = registry.createDefault
    assert(default.isEmpty)
  }
}
