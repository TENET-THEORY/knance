package io.tenetinc.knance.marketdata.companyoverview

import io.tenetinc.knance.marketdata.companyoverview.CompanyOverview

interface CompanyOverviewDataStore {
  fun saveCompanyOverviews(companyOverviews: List<CompanyOverview>)

  fun loadCompanyOverviews(symbols: List<String>): List<CompanyOverview>
}