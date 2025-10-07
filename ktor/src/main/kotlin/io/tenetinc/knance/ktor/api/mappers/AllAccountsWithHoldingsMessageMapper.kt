package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.domain.repository.AllAccountsWithHoldingsBulkQuoteMessage
import io.tenetinc.knance.domain.repository.AllAccountsWithHoldingsLoadingFromDatabaseMessage
import io.tenetinc.knance.domain.repository.AllAccountsWithHoldingsMessage
import io.tenetinc.knance.domain.repository.AllAccountsWithHoldingsWithoutMarketDataMessage
import io.tenetinc.knance.domain.repository.AllAccountsWithMarketDataHoldingsMessage

typealias SerializableAllAccountsWithHoldingsMessage =
    io.tenetinc.knance.common.api.model.AllAccountsWithHoldingsMessage

fun AllAccountsWithHoldingsMessage.toSerializable(): SerializableAllAccountsWithHoldingsMessage {
  return when (this) {
    is AllAccountsWithHoldingsLoadingFromDatabaseMessage ->
        SerializableAllAccountsWithHoldingsMessage(
            type = "AllAccountsWithHoldingsLoadingFromDatabaseMessage")
    is AllAccountsWithHoldingsWithoutMarketDataMessage ->
        SerializableAllAccountsWithHoldingsMessage(
            type = "AllAccountsWithHoldingsWithoutMarketDataMessage",
            accounts = accounts.map { it.toSerializable() })
    is AllAccountsWithHoldingsBulkQuoteMessage ->
        SerializableAllAccountsWithHoldingsMessage(
            type = "AllAccountsWithHoldingsBulkQuoteMessage",
            bulkQuoteMessage = bulkQuoteMessage.toSerializable())
    is AllAccountsWithMarketDataHoldingsMessage ->
        SerializableAllAccountsWithHoldingsMessage(
            type = "AllAccountsWithMarketDataHoldingsMessage",
            accounts = accounts.map { it.toSerializable() })
  }
}
