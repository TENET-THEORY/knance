package io.tenetinc.finance.domain.model

import io.tenetinc.finance.domain.model.security.ETF
import io.tenetinc.finance.domain.model.security.Stock

data class Assets(
    val cashHoldings: List<Cash> = emptyList(),
    val stockHoldings: List<Stock> = emptyList(),
    val etfHoldings: List<ETF> = emptyList(),
)
