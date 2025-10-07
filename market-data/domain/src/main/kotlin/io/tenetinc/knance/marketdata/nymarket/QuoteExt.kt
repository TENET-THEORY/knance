package io.tenetinc.knance.marketdata.nymarket

import io.tenetinc.knance.marketdata.model.Quote

fun Quote.needsRefresh(): Boolean {
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

fun List<Quote>.getSymbolsToRefresh(): List<String> {
  return filter { it.needsRefresh() }.map { it.symbol }
}
