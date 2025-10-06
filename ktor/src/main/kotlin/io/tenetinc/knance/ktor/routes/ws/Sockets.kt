package io.tenetinc.knance.ktor.routes.ws

import io.ktor.server.routing.Route
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.close
import io.tenetinc.knance.domain.repository.AccountRepository
import io.tenetinc.knance.domain.repository.RealTimeDataLiveAccountRepository
import io.tenetinc.knance.ktor.api.mappers.toSerializable

fun Route.sockets(accountRepository: RealTimeDataLiveAccountRepository) {

  // send holdings
  webSocket("/accounts/full") {
    val accounts = accountRepository.allAccountsWithHoldings().collect {
    }
    val serializedAccounts = accounts.map { it.toSerializable() }
    sendSerialized(serializedAccounts)
    close()
  }
}