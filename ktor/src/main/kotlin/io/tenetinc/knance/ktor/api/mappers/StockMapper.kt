package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.domain.model.security.Stock

typealias SerializableStock = io.tenetinc.knance.common.api.model.security.Stock

fun Stock.toSerializable(): SerializableStock {
  return SerializableStock(
      id = id,
      symbol = symbol,
      quantity = quantity,
      costBasis = costBasis,
      priceData = priceData?.toSerializable(),
      divYield = divYield,
      peRatio = peRatio,
      earningsYield = earningsYield)
}
