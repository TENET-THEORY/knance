package io.tenetinc.knance.common.api.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable
@JsExport
data class Cash(
    val id: Int? = null,
    val amount: Float,
    val currency: String,
    val usdValue: Float? = null
)
