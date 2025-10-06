package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.marketdata.repository.live.BulkQuoteMessage

typealias SerializableBulkQuoteMessage = io.tenetinc.knance.common.api.model.BulkQuoteMessage

fun BulkQuoteMessage.toSerializable(): SerializableBulkQuoteMessage {
    return when (this) {
        is io.tenetinc.knance.marketdata.repository.live.LoadingCachedQuotes -> 
            io.tenetinc.knance.common.api.model.LoadingCachedQuotes
        is io.tenetinc.knance.marketdata.repository.live.LoadedCachedQuotes -> 
            io.tenetinc.knance.common.api.model.LoadedCachedQuotes(quotes.map { it.toSerializable() })
        is io.tenetinc.knance.marketdata.repository.live.SymbolsToRefresh -> 
            io.tenetinc.knance.common.api.model.SymbolsToRefresh(symbols)
        is io.tenetinc.knance.marketdata.repository.live.UpdatedQuotes -> 
            io.tenetinc.knance.common.api.model.UpdatedQuotes(quotes.map { it.toSerializable() })
    }
}
