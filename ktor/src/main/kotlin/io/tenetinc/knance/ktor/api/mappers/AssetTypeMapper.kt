package io.tenetinc.knance.ktor.api.mappers

import io.tenetinc.knance.domain.model.AssetType

typealias SerializableAssetType = io.tenetinc.knance.common.api.model.AssetType

fun AssetType.toSerializable(): SerializableAssetType {
  return SerializableAssetType(name = this.name)
}
