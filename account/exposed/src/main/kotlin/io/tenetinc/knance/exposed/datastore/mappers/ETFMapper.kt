package io.tenetinc.knance.exposed.datastore.mappers

import io.tenetinc.knance.domain.model.security.ETF
import io.tenetinc.knance.exposed.db.ETFDAO

internal fun ETFDAO.toDomain() =
    ETF(
        id = id.value,
        symbol = symbol,
        quantity = quantity,
        costBasis = costBasis,
        assetType = assetType)
