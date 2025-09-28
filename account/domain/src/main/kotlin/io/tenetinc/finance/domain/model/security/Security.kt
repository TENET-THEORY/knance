package io.tenetinc.finance.domain.model.security

interface Security {
  val id: Int?
  val symbol: String
  val quantity: Float
  val costBasis: Float
  val priceData: PriceData?
}
