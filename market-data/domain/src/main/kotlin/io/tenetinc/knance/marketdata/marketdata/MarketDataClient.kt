package io.tenetinc.knance.marketdata.marketdata

interface MarketDataClient {
  suspend fun getQuotes(symbols: List<String>): List<Quote>
}