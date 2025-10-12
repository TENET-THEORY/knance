package io.tenetinc.knance.marketdata.companyoverview

import java.time.LocalDate

fun CompanyOverview.needsDailyRefresh(): Boolean {
  val now = LocalDate.now()
  val lastUpdated = this.latestQuarter.let {
    try {
      LocalDate.parse(it)
    } catch (e: Exception) {
      null
    }
  } ?: return true
  
  return lastUpdated.isBefore(now.minusDays(1))
}

fun List<CompanyOverview>.getSymbolsToRefresh(): List<String> {
  return filter { it.needsDailyRefresh() }.map { it.symbol }
}
