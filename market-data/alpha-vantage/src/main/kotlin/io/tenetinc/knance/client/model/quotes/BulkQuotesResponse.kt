package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.quotes

import kotlinx.serialization.Serializable

@Serializable
data class BulkQuotesResponse(
    val endpoint: String,
    val message: String,
    val data: List<RealtimeQuote>
)
