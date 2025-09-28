package io.tenetinc.knance.common.api.model.security

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable
@JsExport
data class PriceData(
    val currentPrice: Float,
    val marketValue: Float,
    val gain: Float,
    val percentGain: Float,
    val dailyGain: Float?,
    val dailyPercentGain: Float?
)
