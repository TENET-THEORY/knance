package io.tenetinc.knance.domain.ext

import io.tenetinc.knance.domain.model.Cash
import io.tenetinc.knance.marketdata.exchangerate.ExchangeRateRepository

const val USD = "USD"

suspend fun Cash.addUsdValue(exchangeRateRepository: ExchangeRateRepository): Cash {
  if (currency == USD) {
    return this.copy(usdValue = amount)
  }
  val exchangeRate =
      exchangeRateRepository.getExchangeRate(fromCurrency = currency, toCurrency = USD)
  return copy(usdValue = amount * exchangeRate.rate)
}
