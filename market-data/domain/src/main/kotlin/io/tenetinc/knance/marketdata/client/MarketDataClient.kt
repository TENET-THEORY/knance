package io.tenetinc.knance.marketdata.client

import io.tenetinc.knance.marketdata.model.Quote

interface MarketDataClient {
  suspend fun getQuotes(symbols: List<String>): List<Quote>
}
