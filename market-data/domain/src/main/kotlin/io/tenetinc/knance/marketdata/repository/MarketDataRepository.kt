package io.tenetinc.knance.marketdata.repository

import io.tenetinc.knance.marketdata.client.MarketDataClient
import io.tenetinc.knance.marketdata.datastore.MarketDataDataStore
import io.tenetinc.knance.marketdata.model.Quote
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class MarketDataRepository(
    private val marketDataClient: MarketDataClient,
    private val marketDataStore: MarketDataDataStore
) {

  suspend fun getBulkQuotes(symbols: List<String>): List<Quote> {
    val cachedQuotes = marketDataStore.loadQuotes(symbols)
    val symbolsToRefresh =
        (if (cachedQuotes.isEmpty()) symbols else getSymbolsToRefresh(cachedQuotes))
    return cachedQuotes +
        marketDataClient.getQuotes(symbolsToRefresh).also { marketDataStore.saveQuotes(it) }
  }

  private fun getSymbolsToRefresh(cachedQuotes: List<Quote>): List<String> {
    return cachedQuotes.filter { it.needsRefresh() }.map { it.symbol }
  }

  private fun Quote.needsRefresh(): Boolean {
    val now = System.currentTimeMillis()
    val marketOpenTime = getMarketOpenTime()
    val marketCloseTime = getMarketCloseTime()
    val afterHoursEndTime = getAfterHoursEndTime()

    val isMarketOpen = now in marketOpenTime until marketCloseTime
    val isAfterHoursTrading = now in marketCloseTime until afterHoursEndTime

    if (!isAfterHoursTrading && isMarketOpen) {
      if (timestamp.time >= afterHoursEndTime) {
        return false
      }
    }
    return timestamp.time < System.currentTimeMillis() - 10 * 60 * 1000
  }

  fun getMarketOpenTime(): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"))
    calendar.time = Date()
    calendar.set(Calendar.HOUR_OF_DAY, 9)
    calendar.set(Calendar.MINUTE, 30)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
  }

  fun getMarketCloseTime(): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"))
    calendar.time = Date()
    calendar.set(Calendar.HOUR_OF_DAY, 16)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
  }

  fun getAfterHoursEndTime(): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"))
    calendar.time = Date()
    calendar.set(Calendar.HOUR_OF_DAY, 20)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
  }
}
