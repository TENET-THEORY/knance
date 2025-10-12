package io.tenetinc.knance.domain.ext

import io.tenetinc.knance.domain.model.security.Stock
import io.tenetinc.knance.domain.model.security.createPriceData
import io.tenetinc.knance.marketdata.marketdata.Quote
import io.tenetinc.knance.marketdata.companyoverview.CompanyOverview

fun Stock.addQuoteData(bulkQuotes: List<Quote>): Stock {
  val quote = bulkQuotes.find { it.symbol == symbol }
  return quote?.let { copy(priceData = createPriceData(it)) } ?: this
}

fun Stock.addCompanyOverviewData(companyOverview: CompanyOverview): Stock {
  return copy(
    divYield = companyOverview.dividendYield,
    peRatio = companyOverview.peRatio,
    earningsYield = companyOverview.eps?.let { eps -> 
      if (eps > 0) eps / (priceData?.currentPrice ?: 0f) * 100f else null 
    }
  )
}

fun Stock.addCompanyOverviewData(bulkCompanyOverviews: List<CompanyOverview>): Stock {
  val companyOverview = bulkCompanyOverviews.find { it.symbol == symbol }
  return companyOverview?.let { addCompanyOverviewData(it) } ?: this
}
