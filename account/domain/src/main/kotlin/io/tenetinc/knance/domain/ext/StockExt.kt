package io.tenetinc.knance.domain.ext

import io.tenetinc.knance.domain.model.security.Stock
import io.tenetinc.knance.domain.model.security.createPriceData
import io.tenetinc.knance.marketdata.model.Quote

fun Stock.addQuoteData(bulkQuotes: List<Quote>): Stock {
  val quote = bulkQuotes.find { it.symbol == symbol }
  return quote?.let { copy(priceData = createPriceData(it)) } ?: this
}