package io.tenetinc.knance.marketdata.marketdata

interface MarketDataDataStore {
  fun saveQuotes(quotes: List<Quote>)

  fun loadQuotes(symbols: List<String>): List<Quote>
}