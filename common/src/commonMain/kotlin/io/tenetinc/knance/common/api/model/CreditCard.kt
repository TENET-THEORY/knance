package io.tenetinc.knance.common.api.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable @JsExport data class CreditCard(val id: Int? = null, val balance: Float)
