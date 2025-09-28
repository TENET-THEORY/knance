package io.tenetinc.knance.domain.datastore

import io.tenetinc.knance.domain.model.Account
import io.tenetinc.knance.domain.model.Cash
import io.tenetinc.knance.domain.model.security.ETF
import io.tenetinc.knance.domain.model.security.Stock

interface AccountDataStore {
  suspend fun findById(id: Int): Account?

  suspend fun allAccounts(): List<Account>

  suspend fun allAccountsWithHoldings(): List<Account>

  suspend fun findByName(name: String): Account?

  suspend fun save(accountName: String): Account

  suspend fun saveCash(accountId: Int, cash: Cash): Cash

  suspend fun saveStock(accountId: Int, stock: Stock): Stock

  suspend fun saveETF(accountId: Int, etf: ETF): ETF

  suspend fun saveCashBatch(accountId: Int, cashList: List<Cash>)

  suspend fun saveStockBatch(accountId: Int, stockList: List<Stock>)

  suspend fun saveETFBatch(accountId: Int, etfList: List<ETF>)

  suspend fun deleteExistingPositions(accountId: Int)
}
