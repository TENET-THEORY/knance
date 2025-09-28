package io.tenetinc.knance.common.api.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable
@JsExport
data class CreateEtfRequest(
    val symbol: String,
    val quantity: Float,
    val costBasis: Float,
    val assetType: String
)
