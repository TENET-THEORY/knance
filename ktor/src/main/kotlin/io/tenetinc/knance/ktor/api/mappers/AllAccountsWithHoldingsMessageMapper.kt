package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.domain.repository.AllAccountsWithHoldingsMessage

typealias SerializableAllAccountsWithHoldingsMessage = io.tenetinc.knance.common.api.model.AllAccountsWithHoldingsMessage

fun AllAccountsWithHoldingsMessage.toSerializable(): SerializableAllAccountsWithHoldingsMessage {
    return when (this) {
        is io.tenetinc.knance.domain.repository.AllAccountsWithHoldingsWithoutMarketDataMessage -> 
            io.tenetinc.knance.common.api.model.AllAccountsWithHoldingsWithoutMarketDataMessage(
                accounts.map { it.toSerializable() }
            )
        is io.tenetinc.knance.domain.repository.AllAccountsWithHoldingsBulkQuoteMessage -> 
            io.tenetinc.knance.common.api.model.AllAccountsWithHoldingsBulkQuoteMessage(
                bulkQuoteMessage.toSerializable()
            )
        is io.tenetinc.knance.domain.repository.AllAccountsWithMarketDataHoldingsMessage -> 
            io.tenetinc.knance.common.api.model.AllAccountsWithMarketDataHoldingsMessage(
                accounts.map { it.toSerializable() }
            )
    }
}
