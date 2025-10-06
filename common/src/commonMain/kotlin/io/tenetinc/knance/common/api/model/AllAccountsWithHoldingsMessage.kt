package io.tenetinc.knance.common.api.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable
@JsExport
data class AllAccountsWithHoldingsMessage(
    val type: String,
    val accounts: List<Account>? = null,
    val bulkQuoteMessage: BulkQuoteMessage? = null
)
