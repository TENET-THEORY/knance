package io.tenetinc.knance.domain.repository

import io.tenetinc.knance.domain.datastore.AccountDataStore
import io.tenetinc.knance.domain.ext.addQuoteData
import io.tenetinc.knance.domain.ext.addUsdValue
import io.tenetinc.knance.domain.model.Account
import io.tenetinc.knance.marketdata.exchangerate.ExchangeRateRepository
import io.tenetinc.knance.marketdata.marketdata.MarketDataRepository

class RealTimeDataAccountRepository(
    accountDataStore: AccountDataStore,
    private val marketDataRepository: MarketDataRepository,
    private val exchangeRateRepository: ExchangeRateRepository
) : AccountRepository(accountDataStore = accountDataStore) {

  override suspend fun findById(id: Int): Account? {
    return super.findById(id)?.withMarketData()
  }

  override suspend fun allAccountsWithHoldings(): List<Account> {
    return super.allAccountsWithHoldings().map { it.withMarketData() }
  }

  private suspend fun Account.withMarketData(): Account {
    try {
      val securitySymbols = stockHoldings.map { it.symbol } + etfHoldings.map { it.symbol }
      val bulkQuotes = marketDataRepository.getBulkQuotes(securitySymbols)
      return copy(
          stockHoldings = stockHoldings.map { it.addQuoteData(bulkQuotes) },
          etfHoldings = etfHoldings.map { it.addQuoteData(bulkQuotes) },
          cashHoldings = cashHoldings.map { it.addUsdValue(exchangeRateRepository) })
    } catch (e: Exception) {
      throw Exception("Failed to fetch stock quotes for account $id", e)
    }
  }
}
