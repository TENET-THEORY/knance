package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.marketdata.marketdata.live.BulkQuoteMessage
import io.tenetinc.knance.marketdata.marketdata.live.LoadedCachedQuotes
import io.tenetinc.knance.marketdata.marketdata.live.LoadingCachedQuotes
import io.tenetinc.knance.marketdata.marketdata.live.SymbolsToRefresh
import io.tenetinc.knance.marketdata.marketdata.live.UpdatedQuotes

typealias SerializableBulkQuoteMessage = io.tenetinc.knance.common.api.model.BulkQuoteMessage

fun BulkQuoteMessage.toSerializable(): SerializableBulkQuoteMessage {
  return when (this) {
    is LoadingCachedQuotes -> SerializableBulkQuoteMessage(type = "LoadingCachedQuotes")
    is LoadedCachedQuotes ->
        SerializableBulkQuoteMessage(
            type = "LoadedCachedQuotes", quotes = quotes.map { it.toSerializable() })
    is SymbolsToRefresh ->
        SerializableBulkQuoteMessage(type = "SymbolsToRefresh", symbols = symbols)
    is UpdatedQuotes ->
        SerializableBulkQuoteMessage(
            type = "UpdatedQuotes", quotes = quotes.map { it.toSerializable() })
  }
}
