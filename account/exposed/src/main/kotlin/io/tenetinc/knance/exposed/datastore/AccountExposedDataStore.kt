package io.tenetinc.knance.exposed.datastore

import io.tenetinc.knance.domain.datastore.AccountDataStore
import io.tenetinc.knance.domain.model.Account
import io.tenetinc.knance.domain.model.Cash
import io.tenetinc.knance.domain.model.security.ETF
import io.tenetinc.knance.domain.model.security.Stock
import io.tenetinc.knance.exposed.datastore.mappers.toDomain
import io.tenetinc.knance.exposed.db.AccountDAO
import io.tenetinc.knance.exposed.db.AccountTable
import io.tenetinc.knance.exposed.db.CashDAO
import io.tenetinc.knance.exposed.db.CashTable
import io.tenetinc.knance.exposed.db.CreditCardDAO
import io.tenetinc.knance.exposed.db.CreditCardTable
import io.tenetinc.knance.exposed.db.ETFDAO
import io.tenetinc.knance.exposed.db.ETFTable
import io.tenetinc.knance.exposed.db.StockDAO
import io.tenetinc.knance.exposed.db.StockTable
import io.tenetinc.knance.exposed.db.suspendTransaction
import kotlin.collections.map
import org.jetbrains.exposed.sql.batchInsert

class AccountExposedDataStore : AccountDataStore {

  override suspend fun findById(id: Int): Account? = suspendTransaction {
    AccountDAO.findById(id)?.toDomain()
  }

  override suspend fun allAccounts(): List<Account> = suspendTransaction {
    AccountTable.select(AccountTable.id, AccountTable.name).map {
      val id = it[AccountTable.id]
      val name = it[AccountTable.name]
      Account(id = id.value, name = name)
    }
  }

  override suspend fun allAccountsWithHoldings(): List<Account> = suspendTransaction {
    AccountDAO.all().map { it.toDomain() }
  }

  override suspend fun findByName(name: String): Account? = suspendTransaction {
    AccountDAO.find { AccountTable.name eq name }.firstOrNull()?.toDomain()
  }

  override suspend fun save(accountName: String): Account = suspendTransaction {
    val newAccount = AccountDAO.new { name = accountName }
    newAccount.toDomain()
  }

  override suspend fun saveCash(accountId: Int, cash: Cash): Cash = suspendTransaction {
    val newCash =
        CashDAO.new {
          amount = cash.amount
          currency = cash.currency
          account = AccountDAO.findById(accountId)!!
        }
    newCash.toDomain()
  }

  override suspend fun saveStock(accountId: Int, stock: Stock): Stock = suspendTransaction {
    val newStock =
        StockDAO.new {
          symbol = stock.symbol
          quantity = stock.quantity
          costBasis = stock.costBasis
          account = AccountDAO.findById(accountId)!!
        }
    newStock.toDomain()
  }

  override suspend fun saveETF(accountId: Int, etf: ETF): ETF = suspendTransaction {
    val newETF =
        ETFDAO.new {
          symbol = etf.symbol
          quantity = etf.quantity
          costBasis = etf.costBasis
          assetType = etf.assetType
          account = AccountDAO.findById(accountId)!!
        }
    newETF.toDomain()
  }

  override suspend fun saveCashBatch(accountId: Int, cashList: List<Cash>) {
    suspendTransaction {
      if (cashList.isEmpty()) return@suspendTransaction emptyList()

      CashTable.batchInsert(cashList) { cash ->
        this[CashTable.amount] = cash.amount
        this[CashTable.currency] = cash.currency
        this[CashTable.accountId] = accountId
      }
    }
  }

  override suspend fun saveStockBatch(accountId: Int, stockList: List<Stock>) {
    suspendTransaction {
      if (stockList.isEmpty()) return@suspendTransaction emptyList()

      StockTable.batchInsert(stockList) { stock ->
        this[StockTable.symbol] = stock.symbol
        this[StockTable.quantity] = stock.quantity
        this[StockTable.costBasis] = stock.costBasis
        this[StockTable.accountId] = accountId
      }
    }
  }

  override suspend fun saveETFBatch(accountId: Int, etfList: List<ETF>) {
    suspendTransaction {
      if (etfList.isEmpty()) return@suspendTransaction emptyList()

      ETFTable.batchInsert(etfList) { etf ->
        this[ETFTable.symbol] = etf.symbol
        this[ETFTable.quantity] = etf.quantity
        this[ETFTable.costBasis] = etf.costBasis
        this[ETFTable.assetType] = etf.assetType
        this[ETFTable.accountId] = accountId
      }
    }
  }

  override suspend fun deleteExistingPositions(accountId: Int) {
    suspendTransaction {
      StockDAO.find { StockTable.accountId eq accountId }.forEach { it.delete() }
      ETFDAO.find { ETFTable.accountId eq accountId }.forEach { it.delete() }
      CashDAO.find { CashTable.accountId eq accountId }.forEach { it.delete() }
      CreditCardDAO.find { CreditCardTable.accountId eq accountId }.forEach { it.delete() }
    }
  }
}
