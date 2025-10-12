package io.tenetinc.knance.marketdata.companyoverview

interface CompanyOverviewClient {
  suspend fun getCompanyOverview(symbol: String): CompanyOverview
}