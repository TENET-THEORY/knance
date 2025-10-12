package io.tenetinc.knance.marketdata.datastore

import io.tenetinc.knance.marketdata.model.CompanyOverview

interface CompanyOverviewDataStore {
  fun saveCompanyOverviews(companyOverviews: List<CompanyOverview>)

  fun loadCompanyOverviews(symbols: List<String>): List<CompanyOverview>
}
