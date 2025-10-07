package io.tenetinc.knance.marketdata.repository.live

import io.tenetinc.knance.marketdata.model.Quote

sealed class BulkQuoteMessage

object LoadingCachedQuotes : BulkQuoteMessage()

data class LoadedCachedQuotes(val quotes: List<Quote>) : BulkQuoteMessage()

data class SymbolsToRefresh(val symbols: List<String>) : BulkQuoteMessage()

data class UpdatedQuotes(val quotes: List<Quote>) : BulkQuoteMessage()
