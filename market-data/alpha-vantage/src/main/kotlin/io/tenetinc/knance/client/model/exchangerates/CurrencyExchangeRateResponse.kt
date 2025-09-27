package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.exchangerates

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyExchangeRateResponse(
    @SerialName("Realtime Currency Exchange Rate") val exchangeRate: CurrencyExchangeRate
)
