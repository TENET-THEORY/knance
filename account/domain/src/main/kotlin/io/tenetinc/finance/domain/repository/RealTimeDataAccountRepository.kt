package io.tenetinc.finance.domain.repository

import io.tenetinc.finance.domain.datastore.AccountDataStore
import io.tenetinc.finance.domain.model.Account
import io.tenetinc.finance.domain.model.Cash
import io.tenetinc.finance.domain.model.security.ETF
import io.tenetinc.finance.domain.model.security.Stock
import io.tenetinc.finance.domain.model.security.createPriceData
import io.tenetinc.knance.marketdata.model.Quote
import io.tenetinc.knance.marketdata.repository.ExchangeRateRepository
import io.tenetinc.knance.marketdata.repository.MarketDataRepository
import kotlin.collections.find

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
          cashHoldings = cashHoldings.map { it.addUsdValue() })
    } catch (e: Exception) {
      throw Exception("Failed to fetch stock quotes for account $id", e)
    }
  }

  private suspend fun Cash.addUsdValue(): Cash {
    if (currency == "USD") {
      return this.copy(usdValue = amount)
    }
    val exchangeRate =
        exchangeRateRepository.getExchangeRate(fromCurrency = currency, toCurrency = "USD")
    return copy(usdValue = amount * exchangeRate.rate)
  }

  private fun Stock.addQuoteData(bulkQuotes: List<Quote>): Stock {
    val quote = bulkQuotes.find { it.symbol == symbol }
    return quote?.let { copy(priceData = createPriceData(it)) } ?: this
  }

  private fun ETF.addQuoteData(bulkQuotes: List<Quote>): ETF {
    val quote = bulkQuotes.find { it.symbol == symbol }
    return quote?.let { copy(priceData = createPriceData(it)) } ?: this
  }
}
