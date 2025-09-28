package io.tenetinc.knance.domain.model.security

data class Stock(
    override val id: Int? = null,
    override val symbol: String,
    override val quantity: Float,
    override val costBasis: Float,
    override val priceData: PriceData? = null,
    val divYield: Float? = null,
    val peRatio: Float? = null,
    val earningsYield: Float? = null
) : Security
