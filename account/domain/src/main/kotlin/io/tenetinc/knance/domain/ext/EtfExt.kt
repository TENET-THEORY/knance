package io.tenetinc.knance.domain.ext

import io.tenetinc.knance.domain.model.security.ETF
import io.tenetinc.knance.domain.model.security.createPriceData
import io.tenetinc.knance.marketdata.model.Quote

fun ETF.addQuoteData(bulkQuotes: List<Quote>): ETF {
  val quote = bulkQuotes.find { it.symbol == symbol }
  return quote?.let { copy(priceData = createPriceData(it)) } ?: this
}
