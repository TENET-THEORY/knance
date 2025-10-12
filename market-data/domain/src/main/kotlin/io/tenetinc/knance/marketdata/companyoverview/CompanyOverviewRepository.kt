package io.tenetinc.knance.marketdata.companyoverview

class CompanyOverviewRepository(
    private val companyOverviewClient: CompanyOverviewClient,
    private val companyOverviewStore: CompanyOverviewDataStore
) {

  suspend fun getCompanyOverviews(symbols: List<String>): List<CompanyOverview> {
    val cachedOverviews = companyOverviewStore.loadCompanyOverviews(symbols)
    val symbolsToRefresh = if (cachedOverviews.isEmpty()) symbols else cachedOverviews.getSymbolsToRefresh()
    
    val freshOverviews = symbolsToRefresh.map { symbol ->
      companyOverviewClient.getCompanyOverview(symbol)
    }
    
    if (freshOverviews.isNotEmpty()) {
      companyOverviewStore.saveCompanyOverviews(freshOverviews)
    }
    
    return cachedOverviews + freshOverviews
  }

  suspend fun getCompanyOverview(symbol: String): CompanyOverview {
    return getCompanyOverviews(listOf(symbol)).first()
  }
}
