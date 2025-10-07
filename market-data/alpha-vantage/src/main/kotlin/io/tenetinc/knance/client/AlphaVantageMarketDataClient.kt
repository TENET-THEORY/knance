package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.quotes.BulkQuotesResponse
import io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.quotes.GlobalQuoteResponse
import io.tenetinc.knance.marketdata.client.MarketDataClient
import io.tenetinc.knance.marketdata.model.Quote
import java.sql.Timestamp
import java.time.LocalDateTime

class AlphaVantageMarketDataClient(private val apiKey: String, private val httpClient: HttpClient) :
    MarketDataClient {

  override suspend fun getQuotes(symbols: List<String>): List<Quote> {
    val bulkQuotes = getBulkQuotes(symbols).data.map {
      try {
        with(it) {
          Quote(
              symbol = symbol,
              price = extendedHoursQuote.toFloatOrNull() ?: previousClose.toFloat(),
              dailyGain = change.toFloatOrNull(),
              dailyPercentGain = changePercent.toFloatOrNull(),
              timestamp = Timestamp.valueOf(it.timestamp))
        }
      } catch (e: Exception) {
        println("Failed to parse quote for symbol $e")
        throw Exception("Failed to parse quote", e)
      }
    }
    val missingSymbols = symbols - bulkQuotes.map { it.symbol }.toSet()
    return missingSymbols.map {
      val globalQuote = getGlobalQuote(it).globalQuote
      Quote(
          symbol = it,
          price = globalQuote.latestPrice.toFloat(),
          dailyGain = globalQuote.change.toFloatOrNull(),
          dailyPercentGain = globalQuote.changePercent.toFloatOrNull(),
          timestamp = Timestamp(System.currentTimeMillis())
      )
    } + bulkQuotes
  }

  private suspend fun getBulkQuotes(symbols: List<String>): BulkQuotesResponse {
    val symbolsParam = symbols.joinToString(",")
    val response =
        httpClient.get {
          parameter("function", "REALTIME_BULK_QUOTES")
          parameter("symbol", symbolsParam)
          parameter("apikey", apiKey)
        }
    return response.body()
  }

  private suspend fun getGlobalQuote(symbol: String): GlobalQuoteResponse {
    val response =
        httpClient.get {
          parameter("function", "GLOBAL_QUOTE")
          parameter("symbol", symbol)
          parameter("apikey", apiKey)
        }
    return response.body()
  }
}
