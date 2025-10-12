package io.tenetinc.knance.marketdata.marketdata

import io.tenetinc.knance.marketdata.marketdata.Quote

interface MarketDataClient {
  suspend fun getQuotes(symbols: List<String>): List<Quote>
}