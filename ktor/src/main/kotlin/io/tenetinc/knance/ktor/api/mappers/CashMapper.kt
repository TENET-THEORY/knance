package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.domain.model.Cash

typealias SerializableCash = io.tenetinc.knance.common.api.model.Cash

fun Cash.toSerializable(): SerializableCash {
  return SerializableCash(id, amount, currency, usdValue)
}
