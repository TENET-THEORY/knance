package io.tenetinc.knance.ktor

import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveMultipart
import io.ktor.utils.io.jvm.javaio.toInputStream

data class CsvFileData(val fileName: String, val content: String)

suspend fun ApplicationCall.extractCsvFile(): CsvFileData? {
  val multipart = receiveMultipart(formFieldLimit = 1024 * 1024 * 100)
  var csvFileData: CsvFileData? = null

  multipart.forEachPart { part ->
    when (part) {
      is PartData.FileItem -> {
        if (part.originalFileName?.endsWith(".csv") == true) {
          val fileName = part.originalFileName ?: "unknown.csv"
          val content =
              part.provider().toInputStream().bufferedReader().use { reader -> reader.readText() }
          csvFileData = CsvFileData(fileName, content)
        }
        part.dispose()
      }
      is PartData.FormItem -> {
        // Handle form fields if needed
        part.dispose()
      }
      else -> {
        part.dispose()
      }
    }
  }

  return csvFileData
}
