package io.tenetinc.knance.exposed.datastore.mappers

import io.tenetinc.knance.domain.model.security.Stock
import io.tenetinc.knance.exposed.db.StockDAO

internal fun StockDAO.toDomain() =
    Stock(
        id = id.value,
        symbol = symbol,
        quantity = quantity,
        costBasis = costBasis,
    )
