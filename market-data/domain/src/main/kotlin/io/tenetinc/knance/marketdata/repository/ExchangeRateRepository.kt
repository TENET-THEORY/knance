package io.tenetinc.knance.marketdata.repository

import io.tenetinc.knance.marketdata.client.ExchangeRateClient
import io.tenetinc.knance.marketdata.model.ExchangeRate

class ExchangeRateRepository(private val exchangeRateClient: ExchangeRateClient) {

  suspend fun getExchangeRate(fromCurrency: String, toCurrency: String = "USD"): ExchangeRate {
    return exchangeRateClient.getExchangeRate(fromCurrency, toCurrency)
  }

  suspend fun getExchangeRates(
      fromCurrencies: List<String>,
      toCurrency: String = "USD"
  ): List<ExchangeRate> {
    return exchangeRateClient.getExchangeRates(fromCurrencies, toCurrency)
  }
}
