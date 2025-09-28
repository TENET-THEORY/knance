package io.tenetinc.knance.common.api.model

import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@Serializable @JsExport data class CreateAccountRequest(val name: String)
