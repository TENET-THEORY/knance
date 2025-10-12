package io.tenetinc.knance.marketdata.client

import io.tenetinc.knance.marketdata.model.CompanyOverview

interface CompanyOverviewClient {
  suspend fun getCompanyOverview(symbol: String): CompanyOverview
}
