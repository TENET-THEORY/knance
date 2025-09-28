package io.tenetinc.knance.common.services

import io.tenetinc.knance.common.api.model.Account
import io.tenetinc.knance.common.api.model.CreateAccountRequest
import io.tenetinc.knance.common.api.model.CreateEtfRequest
import io.tenetinc.knance.common.api.model.security.Etf
import kotlin.js.Promise
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

@JsExport
class AccountsViewModel {
  fun fetchAccounts(): Promise<List<Account>> =
      GlobalScope.promise { io.tenetinc.knance.common.services.fetchAccounts() }

  fun fetchAccountsWithHoldings(): Promise<List<Account>> =
      GlobalScope.promise { io.tenetinc.knance.common.services.fetchAccountsWithHoldings() }

  fun fetchAccount(accountId: Int): Promise<Account> =
      GlobalScope.promise { io.tenetinc.knance.common.services.fetchAccount(accountId = accountId) }

  fun createEtf(accountId: Int, request: CreateEtfRequest): Promise<Etf> =
      GlobalScope.promise { io.tenetinc.knance.common.services.createEtf(accountId, request) }

  fun createAccount(request: CreateAccountRequest): Promise<Account> =
      GlobalScope.promise { io.tenetinc.knance.common.services.createAccount(request) }
}
