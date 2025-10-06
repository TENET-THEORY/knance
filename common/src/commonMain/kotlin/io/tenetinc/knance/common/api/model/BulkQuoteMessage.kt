package io.tenetinc.knance.common.api.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable
@JsExport
sealed class BulkQuoteMessage

@Serializable
@JsExport
object LoadingCachedQuotes : BulkQuoteMessage()

@Serializable
@JsExport
data class LoadedCachedQuotes(val quotes: List<Quote>) : BulkQuoteMessage()

@Serializable
@JsExport
data class SymbolsToRefresh(val symbols: List<String>) : BulkQuoteMessage()

@Serializable
@JsExport
data class UpdatedQuotes(val quotes: List<Quote>) : BulkQuoteMessage()
