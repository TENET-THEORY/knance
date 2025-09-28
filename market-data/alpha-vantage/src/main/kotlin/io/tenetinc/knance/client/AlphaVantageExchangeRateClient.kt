package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.exchangerates.CurrencyExchangeRateResponse
import io.tenetinc.knance.marketdata.client.ExchangeRateClient
import io.tenetinc.knance.marketdata.model.ExchangeRate

class AlphaVantageExchangeRateClient(
    private val apiKey: String,
    private val httpClient: HttpClient
) : ExchangeRateClient {

  override suspend fun getExchangeRates(
      fromCurrencies: List<String>,
      toCurrency: String
  ): List<ExchangeRate> {
    return fromCurrencies.map { fromCurrency -> getExchangeRate(fromCurrency, toCurrency) }
  }

  override suspend fun getExchangeRate(fromCurrency: String, toCurrency: String): ExchangeRate {
    if (fromCurrency == toCurrency) {
      return ExchangeRate(
          fromCurrency = fromCurrency,
          toCurrency = toCurrency,
          rate = 1.0f,
          timestamp = System.currentTimeMillis(),
          lastRefreshed = "")
    }

    val response =
        httpClient.get {
          parameter("function", "CURRENCY_EXCHANGE_RATE")
          parameter("from_currency", fromCurrency)
          parameter("to_currency", toCurrency)
          parameter("apikey", apiKey)
        }

    val data = response.body<CurrencyExchangeRateResponse>()
    return ExchangeRate(
        fromCurrency = data.exchangeRate.fromCurrencyCode,
        toCurrency = data.exchangeRate.toCurrencyCode,
        rate = data.exchangeRate.exchangeRate.toFloat(),
        timestamp = System.currentTimeMillis(),
        lastRefreshed = data.exchangeRate.lastRefreshed)
  }
}
