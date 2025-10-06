package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.domain.repository.AllAccountsWithHoldingsMessage

typealias SerializableAllAccountsWithHoldingsMessage = io.tenetinc.knance.common.api.model.AllAccountsWithHoldingsMessage

fun AllAccountsWithHoldingsMessage.toSerializable(): SerializableAllAccountsWithHoldingsMessage {
    return when (this) {
        is io.tenetinc.knance.domain.repository.AllAccountsWithHoldingsWithoutMarketDataMessage -> 
            io.tenetinc.knance.common.api.model.AllAccountsWithHoldingsMessage(
                type = "AllAccountsWithHoldingsWithoutMarketDataMessage",
                accounts = accounts.map { it.toSerializable() }
            )
        is io.tenetinc.knance.domain.repository.AllAccountsWithHoldingsBulkQuoteMessage -> 
            io.tenetinc.knance.common.api.model.AllAccountsWithHoldingsMessage(
                type = "AllAccountsWithHoldingsBulkQuoteMessage",
                bulkQuoteMessage = bulkQuoteMessage.toSerializable()
            )
        is io.tenetinc.knance.domain.repository.AllAccountsWithMarketDataHoldingsMessage -> 
            io.tenetinc.knance.common.api.model.AllAccountsWithHoldingsMessage(
                type = "AllAccountsWithMarketDataHoldingsMessage",
                accounts = accounts.map { it.toSerializable() }
            )
    }
}
