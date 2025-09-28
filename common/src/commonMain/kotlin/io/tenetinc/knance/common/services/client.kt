package io.tenetinc.knance.common.services

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json

fun createClient(urlString: String) = HttpClient {
  install(ContentNegotiation) { json() }
  defaultRequest { url(urlString = urlString) }
}
