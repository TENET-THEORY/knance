package io.tenetinc.knance.marketdata.companyoverview


class CompanyOverviewRamDataStore : CompanyOverviewDataStore {

  var companyOverviewsMap: Map<String, CompanyOverview> = emptyMap()

  override fun saveCompanyOverviews(companyOverviews: List<CompanyOverview>) {
    companyOverviewsMap = companyOverviewsMap + companyOverviews.associateBy { it.symbol }
  }

  override fun loadCompanyOverviews(symbols: List<String>): List<CompanyOverview> {
    return companyOverviewsMap.filterKeys { symbols.contains(it) }.values.toList()
  }
}