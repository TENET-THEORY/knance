package io.tenetinc.knance.ktor.ai

import io.tenetinc.knance.domain.model.AssetType


interface FinanceClassifier {
  suspend fun classifySecurityType(symbol: String): String

  suspend fun classifyAssetType(symbol: String): AssetType
}

class LlmFinanceClassifier(val aiApiKey: String) : FinanceClassifier {

  val securityTypeClassifier =
      Llm(
          apiKey = aiApiKey,
          systemPrompt =
              "You are a financial expert. Classify if the given symbol is an ETF or a stock. Respond with only 'ETF' or 'STOCK'.")

  val assetTypeClassifier =
      Llm(
          apiKey = aiApiKey,
          systemPrompt =
              "You are a financial expert. Classify the given ETF symbol into one of these asset types: stocks, bonds, t_bills, gold, or oil. Consider the ETF's name, holdings, and investment strategy. Respond with only the asset type in all caps.")

  override suspend fun classifySecurityType(symbol: String): String {
    return securityTypeClassifier.prompt("Classify $symbol")
  }

  override suspend fun classifyAssetType(symbol: String): AssetType {
    return AssetType.valueOf(assetTypeClassifier.prompt("Classify $symbol"))
  }
}
