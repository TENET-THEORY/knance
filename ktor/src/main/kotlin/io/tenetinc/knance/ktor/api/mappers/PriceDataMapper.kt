package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.domain.model.security.PriceData

typealias SerializablePriceData = io.tenetinc.knance.common.api.model.security.PriceData

fun PriceData.toSerializable(): SerializablePriceData {
  return SerializablePriceData(
      currentPrice = currentPrice,
      marketValue = marketValue,
      gain = gain,
      percentGain = percentGain,
      dailyGain = dailyGain,
      dailyPercentGain = dailyPercentGain)
}
