package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.domain.model.Account

typealias SerializableAccount = io.tenetinc.knance.common.api.model.Account

fun Account.toSerializable(): SerializableAccount {
  return SerializableAccount(
      id = id,
      name = name,
      cashHoldings = cashHoldings.map { it.toSerializable() },
      stockHoldings = stockHoldings.map { it.toSerializable() },
      etfHoldings = etfHoldings.map { it.toSerializable() },
      creditCards = creditCards.map { it.toSerializable() })
}
