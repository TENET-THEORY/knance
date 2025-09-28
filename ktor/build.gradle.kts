plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.ktor)
  alias(libs.plugins.kotlinx.serialization)
}

application { mainClass = "io.ktor.server.netty.EngineMain" }

dependencies {
  implementation(projects.marketData.alphaVantage)
  implementation(projects.marketData.domain)
  implementation(projects.common)
  implementation(projects.account.domain)
  implementation(projects.account.exposed)
  implementation(libs.koog)
  implementation(libs.ktor.server.call.logging)
  implementation(libs.ktor.server.core)
  implementation(libs.ktor.server.cors)
  implementation(libs.ktor.server.content.negotiation)
  implementation(libs.ktor.server.config.yaml)
  implementation(libs.ktor.server.netty)
  implementation(libs.ktor.serialization.json)
  implementation(libs.logback.classic)
}
