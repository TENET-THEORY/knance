package io.tenetinc.knance.marketdata.companyoverview


interface CompanyOverviewDataStore {
  fun saveCompanyOverviews(companyOverviews: List<CompanyOverview>)

  fun loadCompanyOverviews(symbols: List<String>): List<CompanyOverview>
}