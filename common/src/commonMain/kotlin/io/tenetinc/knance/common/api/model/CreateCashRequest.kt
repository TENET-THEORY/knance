package io.tenetinc.knance.common.api.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable
@JsExport
data class CreateCashRequest(
    val amount: Float,
    val currency: String
)
