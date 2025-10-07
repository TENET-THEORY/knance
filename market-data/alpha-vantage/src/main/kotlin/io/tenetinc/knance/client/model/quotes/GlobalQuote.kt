package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.quotes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GlobalQuote(
  @SerialName("01. symbol") val symbol: String,
  @SerialName("02. open") val open: String,
  @SerialName("03. high") val high: String,
  @SerialName("04. low") val low: String,
  @SerialName("05. price") val latestPrice: String,
  @SerialName("06. volume") val volume: String,
  @SerialName("07. latest trading day")
  val latestTradingDay: String,
  @SerialName("08. previous close") val previousClose: String,
  @SerialName("09. change") val change: String,
  @SerialName("10. change percent")
  val changePercent: String
)