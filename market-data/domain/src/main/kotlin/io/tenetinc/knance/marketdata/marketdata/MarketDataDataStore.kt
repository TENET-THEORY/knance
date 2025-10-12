package io.tenetinc.knance.marketdata.marketdata

import io.tenetinc.knance.marketdata.marketdata.Quote

interface MarketDataDataStore {
  fun saveQuotes(quotes: List<Quote>)

  fun loadQuotes(symbols: List<String>): List<Quote>
}