package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.marketdata.model.Quote

typealias SerializableQuote = io.tenetinc.knance.common.api.model.Quote

fun Quote.toSerializable(): SerializableQuote {
    return SerializableQuote(
        symbol = symbol,
        price = price,
        dailyGain = dailyGain,
        dailyPercentGain = dailyPercentGain,
        timestamp = timestamp.toString()
    )
}
