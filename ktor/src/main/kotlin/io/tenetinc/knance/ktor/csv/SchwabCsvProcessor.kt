package io.tenetinc.knance.ktor.csv

import io.tenetinc.knance.domain.model.Assets
import io.tenetinc.knance.domain.model.Cash
import io.tenetinc.knance.domain.model.security.ETF
import io.tenetinc.knance.domain.model.security.Stock
import io.tenetinc.knance.ktor.CsvFileData
import io.tenetinc.knance.ktor.ai.FinanceClassifier

class SchwabCsvProcessor(
    financeClassifier: FinanceClassifier,
    csvFileData: CsvFileData,
) :
    CsvProcessor(
        financeClassifier = financeClassifier,
        csvFileData = csvFileData,
        csvType = CsvType.SCHWAB) {

  override val portfolioLines: List<String> = lines.subList(4, lines.size)
  override val headers: List<String> = parseCsvLine(lines[3])

  override fun isValidCsv(): Boolean = lines.isNotEmpty()

  override suspend fun findAssets(): Assets {
    val stocks = mutableListOf<Stock>()
    val etfs = mutableListOf<ETF>()
    val cashPositions = mutableListOf<Cash>()
    extractPositions(stocks, etfs, cashPositions)
    return Assets(cashHoldings = cashPositions, stockHoldings = stocks, etfHoldings = etfs)
  }

  override suspend fun processPosition(
      row: Map<String, String>,
      stocks: MutableList<Stock>,
      etfs: MutableList<ETF>,
      cashPositions: MutableList<Cash>
  ) {
    val symbol = row["Symbol"] ?: return
    if (symbol == "Account Total") return

    val securityType = row["Security Type"] ?: return

    if (symbol == "Cash & Cash Investments") {
      val amountStr = row["Mkt Val (Market Value)"]?.replace("$", "")?.replace(",", "") ?: return
      val amount = amountStr.toFloatOrNull() ?: return
      val cash = Cash(amount = amount, currency = "USD")
      cashPositions.add(cash)
      return
    }

    val quantityStr = row["Qty (Quantity)"]?.replace(",", "") ?: return
    val quantity = quantityStr.toFloatOrNull() ?: return
    val costBasisStr = row["Cost Basis"]?.replace("$", "")?.replace(",", "") ?: return
    val costBasis = costBasisStr.toFloatOrNull() ?: return

    if (securityType == "ETFs & Closed End Funds") {
      val assetType = financeClassifier.classifyAssetType(symbol)
      val etf =
          ETF(symbol = symbol, quantity = quantity, costBasis = costBasis, assetType = assetType)
      etfs.add(etf)
    } else if (securityType == "Equity") {
      val stock = Stock(symbol = symbol, quantity = quantity, costBasis = costBasis)
      stocks.add(stock)
    }
  }
}
