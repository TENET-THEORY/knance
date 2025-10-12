package io.tenetinc.knance.marketdata.marketdata

class MarketDataRamDataStore : MarketDataDataStore {

  var quotesMap: Map<String, Quote> = emptyMap()

  override fun saveQuotes(quotes: List<Quote>) {
    quotesMap = quotesMap + quotes.associateBy { it.symbol }
  }

  override fun loadQuotes(symbols: List<String>): List<Quote> {
    return quotesMap.filterKeys { symbols.contains(it) }.values.toList()
  }
}