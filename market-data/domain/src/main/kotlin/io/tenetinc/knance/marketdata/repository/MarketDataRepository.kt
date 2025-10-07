package io.tenetinc.knance.marketdata.repository

import io.tenetinc.knance.marketdata.client.MarketDataClient
import io.tenetinc.knance.marketdata.datastore.MarketDataDataStore
import io.tenetinc.knance.marketdata.model.Quote
import io.tenetinc.knance.marketdata.nymarket.getSymbolsToRefresh

class MarketDataRepository(
    private val marketDataClient: MarketDataClient,
    private val marketDataStore: MarketDataDataStore
) {

  suspend fun getBulkQuotes(symbols: List<String>): List<Quote> {
    val cachedQuotes = marketDataStore.loadQuotes(symbols)
    val symbolsToRefresh =
        if (cachedQuotes.isEmpty()) symbols else cachedQuotes.getSymbolsToRefresh()
    return cachedQuotes +
        marketDataClient.getQuotes(symbolsToRefresh).also { marketDataStore.saveQuotes(it) }
  }
}
