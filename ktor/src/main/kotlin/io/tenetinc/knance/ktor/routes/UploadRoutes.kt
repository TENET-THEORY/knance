package io.tenetinc.knance.ktor.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.tenetinc.knance.common.api.model.UploadResponse
import io.tenetinc.knance.ktor.csv.CsvUploadService
import io.tenetinc.knance.ktor.extractCsvFile

fun Route.uploadRoutes(csvUploadService: CsvUploadService) {
  route("/upload") {
    post("/ibkr") {
      try {
        val csvFileData = call.extractCsvFile()
        val result =
            if (csvFileData != null) {
              csvUploadService.uploadIbkrPositions(csvFileData)
            } else {
              UploadResponse(false, "No CSV file provided")
            }

        call.respond(HttpStatusCode.OK, result)
      } catch (e: Exception) {
        println("Failed to upload IBKR CSV")
        println(e)
        call.respond(HttpStatusCode.InternalServerError, "Failed to upload IBKR CSV")
      }
    }

    post("/schwab") {
      try {
        val csvFileData = call.extractCsvFile()
        val result =
            if (csvFileData != null) {
              csvUploadService.uploadSchwabPositions(csvFileData)
            } else {
              UploadResponse(false, "No CSV file provided")
            }

        call.respond(HttpStatusCode.OK, result)
      } catch (e: Exception) {
        println("Failed to upload Schwab CSV")
        println(e)
        call.respond(HttpStatusCode.InternalServerError, "Failed to upload Schwab CSV")
      }
    }
  }
}
