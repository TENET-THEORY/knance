package io.tenetinc.finance.domain.model.security

import io.tenetinc.knance.marketdata.model.Quote

fun Security.createPriceData(quote: Quote): PriceData {
  val currentPrice = quote.price
  val marketValue = currentPrice * quantity
  val gain = marketValue - costBasis
  val percentGain = (marketValue - costBasis) / costBasis * 100
  return PriceData(
      currentPrice = currentPrice,
      marketValue = marketValue,
      gain = gain,
      percentGain = percentGain,
      dailyGain = quote.dailyGain,
      dailyPercentGain = quote.dailyPercentGain)
}
