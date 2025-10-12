package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class AlphaVantageCompanyOverviewClient(
  private val apiKey: String,
  private val httpClient: HttpClient
) {

  suspend fun getCompanyOverview(symbol: String) {
    val response = httpClient.get {
      parameter(ParameterKeys.FUNCTION, FunctionValues.OVERVIEW)
      parameter(ParameterKeys.SYMBOL, symbol)
      parameter(ParameterKeys.API_KEY, apiKey)
    }
  }
}