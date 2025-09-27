package io.tenetinc.knance.marketdata.model

import java.sql.Timestamp

data class Quote(
    val symbol: String,
    val price: Float,
    val dailyGain: Float?,
    val dailyPercentGain: Float?,
    val timestamp: Timestamp
)
