package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.domain.model.CreditCard

typealias SerializableCreditCard = io.tenetinc.knance.common.api.model.CreditCard

fun CreditCard.toSerializable(): SerializableCreditCard {
  return SerializableCreditCard(id = id, balance = balance)
}
