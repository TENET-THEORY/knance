package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.overview.CompanyOverviewResponse
import io.tenetinc.knance.marketdata.companyoverview.CompanyOverviewClient
import io.tenetinc.knance.marketdata.companyoverview.CompanyOverview

typealias CompanyOverviewApi = io.tenetinc.finance.alphavantage.io.tenetinc.knance.client.model.overview.CompanyOverview

class AlphaVantageCompanyOverviewClient(
  private val apiKey: String,
  private val httpClient: HttpClient
) : CompanyOverviewClient {

  override suspend fun getCompanyOverview(symbol: String): CompanyOverview {
    val response = httpClient.get {
      parameter(ParameterKeys.FUNCTION, FunctionValues.OVERVIEW)
      parameter(ParameterKeys.SYMBOL, symbol)
      parameter(ParameterKeys.API_KEY, apiKey)
    }
    
    val data = response.body<CompanyOverviewResponse>()
    return mapToDomainModel(data.companyOverview)
  }
  
  private fun mapToDomainModel(apiModel: CompanyOverviewApi): CompanyOverview {
    return CompanyOverview(
      symbol = apiModel.symbol,
      assetType = apiModel.assetType,
      name = apiModel.name,
      description = apiModel.description,
      cik = apiModel.cik,
      exchange = apiModel.exchange,
      currency = apiModel.currency,
      country = apiModel.country,
      sector = apiModel.sector,
      industry = apiModel.industry,
      address = apiModel.address,
      officialSite = apiModel.officialSite,
      fiscalYearEnd = apiModel.fiscalYearEnd,
      latestQuarter = apiModel.latestQuarter,
      marketCapitalization = apiModel.marketCapitalization.toLongOrNull(),
      ebitda = apiModel.ebitda.toLongOrNull(),
      peRatio = apiModel.peRatio.toFloatOrNull(),
      pegRatio = apiModel.pegRatio.toFloatOrNull(),
      bookValue = apiModel.bookValue.toFloatOrNull(),
      dividendPerShare = apiModel.dividendPerShare.toFloatOrNull(),
      dividendYield = apiModel.dividendYield.toFloatOrNull(),
      eps = apiModel.eps.toFloatOrNull(),
      revenuePerShareTTM = apiModel.revenuePerShareTTM.toFloatOrNull(),
      profitMargin = apiModel.profitMargin.toFloatOrNull(),
      operatingMarginTTM = apiModel.operatingMarginTTM.toFloatOrNull(),
      returnOnAssetsTTM = apiModel.returnOnAssetsTTM.toFloatOrNull(),
      returnOnEquityTTM = apiModel.returnOnEquityTTM.toFloatOrNull(),
      revenueTTM = apiModel.revenueTTM.toLongOrNull(),
      grossProfitTTM = apiModel.grossProfitTTM.toLongOrNull(),
      dilutedEPSTTM = apiModel.dilutedEPSTTM.toFloatOrNull(),
      quarterlyEarningsGrowthYOY = apiModel.quarterlyEarningsGrowthYOY.toFloatOrNull(),
      quarterlyRevenueGrowthYOY = apiModel.quarterlyRevenueGrowthYOY.toFloatOrNull(),
      analystTargetPrice = apiModel.analystTargetPrice.toFloatOrNull(),
      analystRatingStrongBuy = apiModel.analystRatingStrongBuy.toIntOrNull(),
      analystRatingBuy = apiModel.analystRatingBuy.toIntOrNull(),
      analystRatingHold = apiModel.analystRatingHold.toIntOrNull(),
      analystRatingSell = apiModel.analystRatingSell.toIntOrNull(),
      analystRatingStrongSell = apiModel.analystRatingStrongSell.toIntOrNull(),
      trailingPE = apiModel.trailingPE.toFloatOrNull(),
      forwardPE = apiModel.forwardPE.toFloatOrNull(),
      priceToSalesRatioTTM = apiModel.priceToSalesRatioTTM.toFloatOrNull(),
      priceToBookRatio = apiModel.priceToBookRatio.toFloatOrNull(),
      evToRevenue = apiModel.evToRevenue.toFloatOrNull(),
      evToEbitda = apiModel.evToEbitda.toFloatOrNull(),
      beta = apiModel.beta.toFloatOrNull(),
      week52High = apiModel.week52High.toFloatOrNull(),
      week52Low = apiModel.week52Low.toFloatOrNull(),
      day50MovingAverage = apiModel.day50MovingAverage.toFloatOrNull(),
      day200MovingAverage = apiModel.day200MovingAverage.toFloatOrNull(),
      sharesOutstanding = apiModel.sharesOutstanding.toLongOrNull(),
      sharesFloat = apiModel.sharesFloat.toLongOrNull(),
      percentInsiders = apiModel.percentInsiders.toFloatOrNull(),
      percentInstitutions = apiModel.percentInstitutions.toFloatOrNull(),
      dividendDate = apiModel.dividendDate.takeIf { it.isNotBlank() },
      exDividendDate = apiModel.exDividendDate.takeIf { it.isNotBlank() }
    )
  }
}