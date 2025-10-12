package io.tenetinc.knance.marketdata.marketdata.live

import io.tenetinc.knance.marketdata.marketdata.MarketDataClient
import io.tenetinc.knance.marketdata.marketdata.MarketDataDataStore
import io.tenetinc.knance.marketdata.marketdata.nymarket.getSymbolsToRefresh
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarketDataLiveRepository(
    private val marketDataClient: MarketDataClient,
    private val marketDataStore: MarketDataDataStore
) {

  fun getBulkQuotes(symbols: List<String>): Flow<BulkQuoteMessage> = flow {
    emit(LoadingCachedQuotes)
    val cachedQuotes = marketDataStore.loadQuotes(symbols)
    emit(LoadedCachedQuotes(cachedQuotes))
    val symbolsToRefresh =
        if (cachedQuotes.isEmpty()) symbols else cachedQuotes.getSymbolsToRefresh()
    emit(SymbolsToRefresh(symbolsToRefresh))
    val updatedQuotes =
        cachedQuotes +
            marketDataClient.getQuotes(symbolsToRefresh).also { marketDataStore.saveQuotes(it) }
    emit(UpdatedQuotes(updatedQuotes))
  }
}
