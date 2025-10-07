package io.tenetinc.knance.ktor.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.tenetinc.knance.common.api.model.CreateAccountRequest
import io.tenetinc.knance.common.api.model.CreateCashRequest
import io.tenetinc.knance.common.api.model.CreateEtfRequest
import io.tenetinc.knance.domain.model.AssetType
import io.tenetinc.knance.domain.model.Cash
import io.tenetinc.knance.domain.model.security.ETF
import io.tenetinc.knance.domain.repository.AccountRepository
import io.tenetinc.knance.ktor.api.mappers.toSerializable

fun Route.accountsRoutes(accountRepository: AccountRepository) {
  route("/accounts") {
    get {
      try {
        val accounts = accountRepository.allAccounts()
        call.respond(accounts.map { it.toSerializable() })
      } catch (e: Exception) {
        println("Failed to fetch accounts")
        println(e)
        call.respond(HttpStatusCode.InternalServerError, "Failed to fetch accounts")
      }
    }
    get("/full") {
      try {
        val accounts = accountRepository.allAccountsWithHoldings()
        val serializedAccounts = accounts.map { it.toSerializable() }
        call.respond(serializedAccounts)
      } catch (e: Exception) {
        println("Failed to fetch accounts")
        println(e)
        call.respond(HttpStatusCode.InternalServerError, "Failed to fetch accounts with holdings")
      }
    }
    get("/{accountId}") {
      call.parameters["accountId"]?.let { accountId ->
        runCatching { accountId.toInt() }
            .onSuccess { id ->
              val account = accountRepository.findById(id)
              account?.let { call.respond(it.toSerializable()) }
                  ?: call.respond(HttpStatusCode.NotFound)
            }
            .onFailure { _ ->
              call.respond(HttpStatusCode.BadRequest, "Invalid account ID format $accountId")
            }
      } ?: call.respond(HttpStatusCode.BadRequest, "Account ID is required")
    }
    post {
      try {
        val request = call.receive<CreateAccountRequest>()
        if (request.name.isBlank()) {
          call.respond(HttpStatusCode.BadRequest, "Account name cannot be empty")
          return@post
        }
        val account = accountRepository.createAccount(request.name)
        call.respond(HttpStatusCode.Created, account.toSerializable())
      } catch (e: Exception) {
        println("Failed to create account")
        println(e)
        call.respond(HttpStatusCode.InternalServerError, "Failed to create account")
      }
    }
    post("/{accountId}/etfs") {
      call.parameters["accountId"]?.let { accountId ->
        runCatching { accountId.toInt() }
            .onSuccess { id ->
              try {
                val request = call.receive<CreateEtfRequest>()
                val assetType =
                    runCatching { AssetType.valueOf(request.assetType) }
                        .getOrElse {
                          call.respond(
                              HttpStatusCode.BadRequest, "Invalid asset type: ${request.assetType}")
                          return@post
                        }
                val etf =
                    ETF(
                        symbol = request.symbol,
                        quantity = request.quantity,
                        costBasis = request.costBasis,
                        assetType = assetType)
                val savedEtf = accountRepository.saveETF(id, etf)
                call.respond(HttpStatusCode.Created, savedEtf.toSerializable())
              } catch (e: Exception) {
                println("Failed to create ETF for account $id")
                println(e)
                call.respond(HttpStatusCode.InternalServerError, "Failed to create ETF")
              }
            }
            .onFailure { _ ->
              call.respond(HttpStatusCode.BadRequest, "Invalid account ID format $accountId")
            }
      } ?: call.respond(HttpStatusCode.BadRequest, "Account ID is required")
    }
    post("/{accountId}/cash") {
      call.parameters["accountId"]?.let { accountId ->
        runCatching { accountId.toInt() }
            .onSuccess { id ->
              try {
                val request = call.receive<CreateCashRequest>()
                if (request.amount <= 0) {
                  call.respond(HttpStatusCode.BadRequest, "Amount must be greater than 0")
                  return@post
                }
                if (request.currency.isBlank()) {
                  call.respond(HttpStatusCode.BadRequest, "Currency cannot be empty")
                  return@post
                }
                val cash = Cash(
                    amount = request.amount,
                    currency = request.currency.uppercase()
                )
                val savedCash = accountRepository.saveCash(id, cash)
                call.respond(HttpStatusCode.Created, savedCash.toSerializable())
              } catch (e: Exception) {
                println("Failed to create cash for account $id")
                println(e)
                call.respond(HttpStatusCode.InternalServerError, "Failed to create cash")
              }
            }
            .onFailure { _ ->
              call.respond(HttpStatusCode.BadRequest, "Invalid account ID format $accountId")
            }
      } ?: call.respond(HttpStatusCode.BadRequest, "Account ID is required")
    }
  }
}
