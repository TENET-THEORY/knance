package io.tenetinc.knance.common.api.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable @JsExport data class UploadResponse(val success: Boolean, val message: String)
