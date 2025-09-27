package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.exchangerates

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyExchangeRate(
    @SerialName("1. From_Currency Code") val fromCurrencyCode: String,
    @SerialName("2. From_Currency Name") val fromCurrencyName: String,
    @SerialName("3. To_Currency Code") val toCurrencyCode: String,
    @SerialName("4. To_Currency Name") val toCurrencyName: String,
    @SerialName("5. Exchange Rate") val exchangeRate: String,
    @SerialName("6. Last Refreshed") val lastRefreshed: String,
    @SerialName("7. Time Zone") val timeZone: String,
    @SerialName("8. Bid Price") val bidPrice: String,
    @SerialName("9. Ask Price") val askPrice: String
)
