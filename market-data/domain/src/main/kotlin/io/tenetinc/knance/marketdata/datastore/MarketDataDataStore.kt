package io.tenetinc.knance.marketdata.datastore

import io.tenetinc.knance.marketdata.model.Quote

interface MarketDataDataStore {
  fun saveQuotes(quotes: List<Quote>)

  fun loadQuotes(symbols: List<String>): List<Quote>
}
