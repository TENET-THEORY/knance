package io.tenetinc.knance.common.api.model

import io.tenetinc.knance.common.api.model.security.Etf
import io.tenetinc.knance.common.api.model.security.Stock
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable
@JsExport
data class Account(
  val id: Int? = null,
  val name: String,
  val cashHoldings: List<Cash> = emptyList(),
  val stockHoldings: List<Stock> = emptyList(),
  val etfHoldings: List<Etf> = emptyList(),
  val creditCards: List<CreditCard> = emptyList()
)
