package io.tenetinc.finance.domain.repository

import io.tenetinc.finance.domain.datastore.AccountDataStore
import io.tenetinc.finance.domain.model.Account
import io.tenetinc.finance.domain.model.Assets
import io.tenetinc.finance.domain.model.security.ETF

open class AccountRepository(private val accountDataStore: AccountDataStore) {
  open suspend fun findById(id: Int): Account? {
    return accountDataStore.findById(id)
  }

  open suspend fun allAccountsWithHoldings(): List<Account> {
    return accountDataStore.allAccountsWithHoldings()
  }

  suspend fun allAccounts(): List<Account> {
    return accountDataStore.allAccounts()
  }

  suspend fun createAccount(name: String): Account {
    return accountDataStore.save(name)
  }

  suspend fun saveETF(accountId: Int, etf: ETF): ETF {
    return accountDataStore.saveETF(accountId, etf)
  }

  suspend fun syncAssets(
      accountName: String,
      assets: Assets,
  ) {
    val account = accountDataStore.findByName(accountName) ?: accountDataStore.save(accountName)
    accountDataStore.deleteExistingPositions(account.id)
    accountDataStore.saveStockBatch(accountId = account.id, stockList = assets.stockHoldings)
    accountDataStore.saveETFBatch(accountId = account.id, etfList = assets.etfHoldings)
    accountDataStore.saveCashBatch(accountId = account.id, cashList = assets.cashHoldings)
  }
}
