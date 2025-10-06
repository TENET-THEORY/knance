package io.tenetinc.knance.common.api.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable
@JsExport
data class Quote(
    val symbol: String,
    val price: Float,
    val dailyGain: Float?,
    val dailyPercentGain: Float?,
    val timestamp: String
)
