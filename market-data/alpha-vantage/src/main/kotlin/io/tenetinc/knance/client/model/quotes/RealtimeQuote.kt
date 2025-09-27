package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.quotes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RealtimeQuote(
    val symbol: String,
    val timestamp: String,
    val open: String,
    val high: String,
    val low: String,
    val close: String,
    val volume: String,
    @SerialName("previous_close") val previousClose: String,
    val change: String, // could be an empty string
    @SerialName("change_percent") val changePercent: String,
    @SerialName("extended_hours_quote") val extendedHoursQuote: String, // could be an empty string
    @SerialName("extended_hours_change") val extendedHoursChange: String,
    @SerialName("extended_hours_change_percent") val extendedHoursChangePercent: String
)
