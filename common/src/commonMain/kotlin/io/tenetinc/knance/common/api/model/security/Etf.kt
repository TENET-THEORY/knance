package io.tenetinc.knance.common.api.model.security

import io.tenetinc.knance.common.api.model.AssetType
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable
@JsExport
data class Etf(
  override val id: Int? = null,
  override val symbol: String,
  override val quantity: Float,
  override val costBasis: Float,
  val assetType: AssetType,
  override val priceData: PriceData? = null
) : Security
