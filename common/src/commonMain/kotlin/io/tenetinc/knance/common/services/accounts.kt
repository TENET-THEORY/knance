package io.tenetinc.knance.common.services

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

const val API_URL = "http://0.0.0.0:8080"

val client = createClient(urlString = API_URL)

suspend fun fetchAccounts(): List<io.tenetinc.knance.common.api.model.Account> {
  return client.get("/accounts").body()
}

suspend fun fetchAccountsWithHoldings(): List<io.tenetinc.knance.common.api.model.Account> {
  return client.get("/accounts/full").body()
}

suspend fun fetchAccount(accountId: Int): io.tenetinc.knance.common.api.model.Account {
  return client.get("/accounts/${accountId}").body()
}

suspend fun createAccount(
    request: io.tenetinc.knance.common.api.model.CreateAccountRequest
): io.tenetinc.knance.common.api.model.Account {
  return client
      .post("/accounts") {
        contentType(ContentType.Application.Json)
        setBody(request)
      }
      .body()
}

suspend fun createEtf(
    accountId: Int,
    request: io.tenetinc.knance.common.api.model.CreateEtfRequest
): io.tenetinc.knance.common.api.model.security.Etf {
  return client
      .post("/accounts/${accountId}/etfs") {
        contentType(ContentType.Application.Json)
        setBody(request)
      }
      .body()
}
