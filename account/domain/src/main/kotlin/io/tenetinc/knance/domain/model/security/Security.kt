package io.tenetinc.knance.domain.model.security

interface Security {
  val id: Int?
  val symbol: String
  val quantity: Float
  val costBasis: Float
  val priceData: PriceData?
}
