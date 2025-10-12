package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.exchangerates.CurrencyExchangeRateResponse
import io.tenetinc.knance.marketdata.client.ExchangeRateClient
import io.tenetinc.knance.marketdata.model.ExchangeRate
import java.util.function.Function

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
          parameter(ParameterKeys.FUNCTION, FunctionValues.CURRENCY_EXCHANGE_RATE)
          parameter(ParameterKeys.FROM_CURRENCY, fromCurrency)
          parameter(ParameterKeys.TO_CURRENCY, toCurrency)
          parameter(ParameterKeys.API_KEY, apiKey)
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
