package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.domain.model.security.ETF

typealias SerializableEtf = io.tenetinc.knance.common.api.model.security.Etf

fun ETF.toSerializable(): SerializableEtf {
  return SerializableEtf(
      id = id,
      symbol = symbol,
      quantity = quantity,
      costBasis = costBasis,
      assetType = assetType.toSerializable(),
      priceData = priceData?.toSerializable())
}
