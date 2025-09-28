package io.tenetinc.knance.exposed.datastore.mappers

import io.tenetinc.knance.domain.model.Cash
import io.tenetinc.knance.exposed.db.CashDAO

internal fun CashDAO.toDomain() = Cash(id = id.value, amount = amount, currency = currency)
