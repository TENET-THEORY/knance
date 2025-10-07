package io.tenetinc.knance.ktor

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import io.tenetinc.knance.domain.repository.AccountRepository
import io.tenetinc.knance.domain.repository.RealTimeDataLiveAccountRepository
import io.tenetinc.knance.ktor.ai.FinanceClassifier
import io.tenetinc.knance.ktor.csv.CsvUploadService
import io.tenetinc.knance.ktor.routes.accountsRoutes
import io.tenetinc.knance.ktor.routes.uploadRoutes
import io.tenetinc.knance.ktor.routes.ws.sockets

fun Application.configureRouting(
    accountRepository: AccountRepository,
    realTimeDataLiveAccountRepository: RealTimeDataLiveAccountRepository,
    financeClassifier: FinanceClassifier
) {
  install(CallLogging)
  install(ContentNegotiation) { json() }
  val csvUploadService = CsvUploadService(accountRepository, financeClassifier)
  routing {
    accountsRoutes(accountRepository)
    uploadRoutes(csvUploadService)
    sockets(realTimeDataLiveAccountRepository)
  }
}
