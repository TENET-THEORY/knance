package io.tenetinc.knance.domain.model

import io.tenetinc.knance.domain.model.security.ETF
import io.tenetinc.knance.domain.model.security.Stock

data class Assets(
    val cashHoldings: List<Cash> = emptyList(),
    val stockHoldings: List<Stock> = emptyList(),
    val etfHoldings: List<ETF> = emptyList(),
)
