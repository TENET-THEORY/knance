package io.tenetinc.knance.ktor.csv

import io.tenetinc.knance.domain.model.Assets
import io.tenetinc.knance.domain.model.Cash
import io.tenetinc.knance.domain.model.security.ETF
import io.tenetinc.knance.domain.model.security.Stock
import io.tenetinc.knance.ktor.CsvFileData
import io.tenetinc.knance.ktor.ai.FinanceClassifier

class IbkrCsvProcessor(financeClassifier: FinanceClassifier, csvFileData: CsvFileData) :
    CsvProcessor(
        financeClassifier = financeClassifier, csvFileData = csvFileData, csvType = CsvType.IBKR) {

  private val portfolioStart = lines.indexOfFirst { line -> line.trim() == "Portfolio" }
  private val cashStart = lines.indexOfFirst { line -> line.trim() == "Cash Balances" }

  override val headers: List<String> =
      lines[portfolioStart + 1].trim().split(",").map { header -> header.trim() }
  override val portfolioLines: List<String> = lines.subList(portfolioStart + 2, cashStart)

  override fun isValidCsv(): Boolean {
    return portfolioStart != -1 && cashStart != -1
  }

  override suspend fun findAssets(): Assets {
    val stocks = mutableListOf<Stock>()
    val etfs = mutableListOf<ETF>()
    val cashPositions = mutableListOf<Cash>()

    extractPositions(stocks, etfs, cashPositions)
    val cashLines = lines.subList(cashStart + 1, lines.size)
    processCashBalances(cashLines, cashPositions)
    return Assets(cashHoldings = cashPositions, stockHoldings = stocks, etfHoldings = etfs)
  }

  override suspend fun processPosition(
      row: Map<String, String>,
      stocks: MutableList<Stock>,
      etfs: MutableList<ETF>,
      cashPositions: MutableList<Cash>
  ) {
    val symbol = row["Financial Instrument Description"] ?: return

    if (symbol == "Cash Balances") {
      val currency = row["Currency"] ?: return
      val amount = row["Position"]?.toFloatOrNull() ?: return
      val cash = Cash(amount = amount, currency = currency)
      cashPositions.add(cash)
      return
    }

    val quantity = row["Position"]?.toFloatOrNull() ?: return
    val avgPrice = row["Average Price"]?.toFloatOrNull() ?: return
    val costBasis = avgPrice * quantity

    val securityType = financeClassifier.classifySecurityType(symbol)

    if (securityType == "ETF") {
      val assetType = financeClassifier.classifyAssetType(symbol)
      val etf =
          ETF(symbol = symbol, quantity = quantity, costBasis = costBasis, assetType = assetType)
      etfs.add(etf)
    } else {
      val stock = Stock(symbol = symbol, quantity = quantity, costBasis = costBasis)
      stocks.add(stock)
    }
  }

  private fun processCashBalances(cashLines: List<String>, cashPositions: MutableList<Cash>) {
    cashLines.forEach { line ->
      if (line.isNotBlank()) {
        val parts = line.trim().split(",").map { it.trim() }
        if (parts.size >= 2 && parts[0] != "Total (in USD)") {
          val currency = parts[0]
          val amount = parts[1].toFloatOrNull()
          if (amount != null) {
            val cash = Cash(amount = amount, currency = currency)
            cashPositions.add(cash)
          }
        }
      }
    }
  }
}
