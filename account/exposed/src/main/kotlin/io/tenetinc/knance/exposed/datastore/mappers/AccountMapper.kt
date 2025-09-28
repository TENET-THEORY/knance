package io.tenetinc.knance.exposed.datastore.mappers

import io.tenetinc.knance.domain.model.Account
import io.tenetinc.knance.exposed.db.AccountDAO

internal fun AccountDAO.toDomain() =
    Account(
        id = id.value,
        name = name,
        cashHoldings = cashHoldings.map { it.toDomain() },
        stockHoldings = stockHoldings.map { it.toDomain() },
        etfHoldings = etfHoldings.map { it.toDomain() },
        creditCards = creditCards.map { it.toDomain() })
