package io.tenetinc.knance.ktor

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.DefaultJson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.ALPHA_VANTAGE_BASE_URL
import io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.AlphaVantageExchangeRateClient
import io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.AlphaVantageMarketDataClient
import io.tenetinc.knance.common.services.createClient
import io.tenetinc.knance.domain.repository.RealTimeDataAccountRepository
import io.tenetinc.knance.domain.repository.RealTimeDataLiveAccountRepository
import io.tenetinc.knance.exposed.configureDatabase
import io.tenetinc.knance.exposed.datastore.AccountExposedDataStore
import io.tenetinc.knance.ktor.ai.LlmFinanceClassifier
import io.tenetinc.knance.marketdata.datastore.MarketDataRamDataStore
import io.tenetinc.knance.marketdata.repository.ExchangeRateRepository
import io.tenetinc.knance.marketdata.repository.MarketDataRepository
import io.tenetinc.knance.marketdata.repository.live.MarketDataLiveRepository
import kotlin.time.Duration.Companion.seconds

fun main(args: Array<String>) {
  io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
  val accountExposedDataStore = AccountExposedDataStore()
  val marketDataClient =
      AlphaVantageMarketDataClient(
          apiKey = environment.config.property("alphavantage.apikey").getString(),
          httpClient = createClient(urlString = ALPHA_VANTAGE_BASE_URL))
  val marketDataDataStore = MarketDataRamDataStore()
  val marketDataRepository =
      MarketDataRepository(
          marketDataClient = marketDataClient, marketDataStore = marketDataDataStore)
  val exchangeRateRepository =
      ExchangeRateRepository(
          exchangeRateClient =
              AlphaVantageExchangeRateClient(
                  apiKey = environment.config.property("alphavantage.apikey").getString(),
                  httpClient = createClient(urlString = ALPHA_VANTAGE_BASE_URL)))
  val accountRepository =
      RealTimeDataAccountRepository(
          accountDataStore = accountExposedDataStore,
          marketDataRepository = marketDataRepository,
          exchangeRateRepository = exchangeRateRepository)
  configureDatabase(
      url = environment.config.property("postgres.url").getString(),
      user = environment.config.property("postgres.user").getString(),
      password = environment.config.property("postgres.password").getString(),
  )
  val llmFinanceClassifier =
      LlmFinanceClassifier(aiApiKey = environment.config.property("openai.apikey").getString())
  install(CORS) {
    allowMethod(HttpMethod.Get)
    allowMethod(HttpMethod.Put)
    allowMethod(HttpMethod.Post)
    allowHeader(HttpHeaders.ContentType)
    anyHost()
  }
  install(WebSockets) {
    contentConverter = KotlinxWebsocketSerializationConverter(DefaultJson)
    pingPeriod = 15.seconds
    timeout = 15.seconds
    maxFrameSize = Long.MAX_VALUE
    masking = false
  }
  configureRouting(
      accountRepository = accountRepository,
      realTimeDataLiveAccountRepository =
          RealTimeDataLiveAccountRepository(
              accountDataStore = accountExposedDataStore,
              marketDataLiveRepository =
                  MarketDataLiveRepository(
                      marketDataClient = marketDataClient, marketDataStore = marketDataDataStore),
              exchangeRateRepository = exchangeRateRepository),
      financeClassifier = llmFinanceClassifier)
}
