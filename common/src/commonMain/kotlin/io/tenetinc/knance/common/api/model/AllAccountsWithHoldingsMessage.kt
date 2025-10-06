package io.tenetinc.knance.common.api.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable
@JsExport
sealed class AllAccountsWithHoldingsMessage

@Serializable
@JsExport
data class AllAccountsWithHoldingsWithoutMarketDataMessage(val accounts: List<Account>) : AllAccountsWithHoldingsMessage()

@Serializable
@JsExport
data class AllAccountsWithHoldingsBulkQuoteMessage(val bulkQuoteMessage: BulkQuoteMessage) : AllAccountsWithHoldingsMessage()

@Serializable
@JsExport
data class AllAccountsWithMarketDataHoldingsMessage(val accounts: List<Account>) : AllAccountsWithHoldingsMessage()
