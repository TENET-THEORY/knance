package io.tenetinc.knance.exposed.datastore.mappers

import io.tenetinc.knance.domain.model.CreditCard
import io.tenetinc.knance.exposed.db.CreditCardDAO

internal fun CreditCardDAO.toDomain() = CreditCard(id = id.value, balance = balance)
