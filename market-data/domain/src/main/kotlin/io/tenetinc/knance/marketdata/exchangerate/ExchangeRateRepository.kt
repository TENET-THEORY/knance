package io.tenetinc.knance.marketdata.exchangerate

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