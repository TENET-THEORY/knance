package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.quotes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GlobalQuoteResponse (
  @SerialName("Global Quote") val globalQuote: GlobalQuote
)