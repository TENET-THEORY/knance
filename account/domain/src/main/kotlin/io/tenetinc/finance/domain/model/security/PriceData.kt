package io.tenetinc.finance.domain.model.security

data class PriceData(
    val currentPrice: Float,
    val marketValue: Float,
    val gain: Float,
    val percentGain: Float,
    val dailyGain: Float?,
    val dailyPercentGain: Float?
)
