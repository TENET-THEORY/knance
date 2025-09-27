package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.quotes.BulkQuotesResponse
import io.tenetinc.knance.marketdata.client.MarketDataClient
import io.tenetinc.knance.marketdata.model.Quote
import java.sql.Timestamp

class AlphaVantageMarketDataClient(private val apiKey: String, private val httpClient: HttpClient) :
  MarketDataClient {

  override suspend fun getQuotes(symbols: List<String>): List<Quote> {
    return getBulkQuotes(symbols).data.map {
      try {
        with(it) {
          Quote(
            symbol = symbol,
            price = extendedHoursQuote.toFloatOrNull() ?: previousClose.toFloat(),
            dailyGain = change.toFloatOrNull(),
            dailyPercentGain = changePercent.toFloatOrNull(),
            timestamp = Timestamp.valueOf(it.timestamp)
          )
        }
      } catch (e: Exception) {
        println("Failed to parse quote for symbol $e")
        throw Exception("Failed to parse quote", e)
      }
    }
  }

  private suspend fun getBulkQuotes(symbols: List<String>): BulkQuotesResponse {
    val symbolsParam = symbols.joinToString(",")
    val response =
        httpClient.get {
          parameter("function", "REALTIME_BULK_QUOTES")
          parameter("symbol", symbolsParam)
          parameter("apikey", apiKey)
        }
    return response.body<BulkQuotesResponse>()
  }
}
