package io.tenetinc.knance.common.api.model.security

import kotlin.js.JsExport

@JsExport
interface Security {
  val id: Int?
  val symbol: String
  val quantity: Float
  val costBasis: Float
  val priceData: PriceData?
}
