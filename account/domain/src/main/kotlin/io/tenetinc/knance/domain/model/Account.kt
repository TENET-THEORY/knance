package io.tenetinc.knance.domain.model

import io.tenetinc.knance.domain.model.security.ETF
import io.tenetinc.knance.domain.model.security.Stock

data class Account(
    val id: Int,
    val name: String,
    val cashHoldings: List<Cash> = emptyList(),
    val stockHoldings: List<Stock> = emptyList(),
    val etfHoldings: List<ETF> = emptyList(),
    val creditCards: List<CreditCard> = emptyList()
)
