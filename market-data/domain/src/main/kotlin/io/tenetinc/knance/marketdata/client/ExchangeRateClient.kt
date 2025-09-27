package io.tenetinc.knance.marketdata.client

import io.tenetinc.knance.marketdata.model.ExchangeRate

interface ExchangeRateClient {

  suspend fun getExchangeRate(fromCurrency: String, toCurrency: String = "USD"): ExchangeRate

  suspend fun getExchangeRates(
      fromCurrencies: List<String>,
      toCurrency: String = "USD"
  ): List<ExchangeRate>
}
