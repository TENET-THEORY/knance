package io.tenetinc.knance.domain.repository

import io.tenetinc.knance.domain.datastore.AccountDataStore
import io.tenetinc.knance.domain.ext.addQuoteData
import io.tenetinc.knance.domain.ext.addUsdValue
import io.tenetinc.knance.domain.model.Account
import io.tenetinc.knance.marketdata.exchangerate.ExchangeRateRepository
import io.tenetinc.knance.marketdata.marketdata.live.MarketDataLiveRepository
import io.tenetinc.knance.marketdata.marketdata.live.UpdatedQuotes
import kotlin.collections.map
import kotlin.collections.plus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RealTimeDataLiveAccountRepository(
    private val accountDataStore: AccountDataStore,
    private val marketDataLiveRepository: MarketDataLiveRepository,
    private val exchangeRateRepository: ExchangeRateRepository
) {

  fun allAccountsWithHoldings(): Flow<AllAccountsWithHoldingsMessage> = flow {
    emit(AllAccountsWithHoldingsLoadingFromDatabaseMessage)
    val allAccountsWithoutMarketData = accountDataStore.allAccountsWithHoldings()
    emit(AllAccountsWithHoldingsWithoutMarketDataMessage(allAccountsWithoutMarketData))
    val accountsWithMarketData = mutableListOf<Account>()
    allAccountsWithoutMarketData.forEach { account ->
      with(account) {
        try {
          val securitySymbols = stockHoldings.map { it.symbol } + etfHoldings.map { it.symbol }
          marketDataLiveRepository.getBulkQuotes(securitySymbols).collect { bulkQuotesMessage ->
            emit(AllAccountsWithHoldingsBulkQuoteMessage(bulkQuotesMessage))
            if (bulkQuotesMessage is UpdatedQuotes) {
              val accountWithMarketData =
                  account.copy(
                      stockHoldings =
                          stockHoldings.map { it.addQuoteData(bulkQuotesMessage.quotes) },
                      etfHoldings = etfHoldings.map { it.addQuoteData(bulkQuotesMessage.quotes) },
                      cashHoldings = cashHoldings.map { it.addUsdValue(exchangeRateRepository) })
              accountsWithMarketData.add(accountWithMarketData)
            }
          }
        } catch (e: Exception) {
          throw Exception("Failed to fetch stock quotes for account $id", e)
        }
      }
    }
    emit(AllAccountsWithMarketDataHoldingsMessage(accountsWithMarketData))
  }
}
