package io.tenetinc.finance.domain.model.security

import io.tenetinc.finance.domain.model.AssetType

data class ETF(
    override val id: Int? = null,
    override val symbol: String,
    override val quantity: Float,
    override val costBasis: Float,
    val assetType: AssetType,
    override val priceData: PriceData? = null
) : Security
