package io.tenetinc.knance.marketdata.model

data class ExchangeRate(
    val fromCurrency: String,
    val toCurrency: String,
    val rate: Float,
    val timestamp: Long,
    val lastRefreshed: String
)
