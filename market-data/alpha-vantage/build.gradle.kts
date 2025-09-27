plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlinx.serialization)
}

dependencies {
  api(projects.marketData.domain)
  api(libs.ktor.client.apache)
  implementation(libs.ktor.client.core)
  implementation(libs.ktor.client.content.negotiation)
  implementation(libs.ktor.serialization.json)
}
