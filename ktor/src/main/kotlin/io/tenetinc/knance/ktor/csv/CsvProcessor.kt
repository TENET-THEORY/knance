package io.tenetinc.knance.ktor.csv

import io.tenetinc.knance.domain.model.Assets
import io.tenetinc.knance.domain.model.Cash
import io.tenetinc.knance.domain.model.security.ETF
import io.tenetinc.knance.domain.model.security.Stock
import io.tenetinc.knance.ktor.CsvFileData
import io.tenetinc.knance.ktor.ai.FinanceClassifier


abstract class CsvProcessor(
  protected val financeClassifier: FinanceClassifier,
  private val csvFileData: CsvFileData,
  private val csvType: CsvType
) {

  protected val content = csvFileData.content
  protected val lines = content.split("\n")

  protected abstract val portfolioLines: List<String>

  protected abstract val headers: List<String>

  fun previewFileData() {
    with(csvFileData) {
      println("${csvType.name} Upload - File: $fileName")
      println("${csvType.name} Upload - Content length: ${content.length}")
      println("${csvType.name} Upload - Content preview: ${content.take(200)}")
    }
  }

  abstract fun isValidCsv(): Boolean

  abstract suspend fun findAssets(): Assets

  protected suspend fun extractPositions(
    stocks: MutableList<Stock>,
    etfs: MutableList<ETF>,
    cashPositions: MutableList<Cash>
  ) {
    portfolioLines.forEach { line ->
      if (line.isNotBlank()) {
        val values = parseCsvLine(line)
        if (values.size >= headers.size) {
          val row = headers.zip(values).toMap()
          processPosition(row, stocks, etfs, cashPositions)
        }
      }
    }
  }

  protected fun parseCsvLine(line: String): List<String> {
    val result = mutableListOf<String>()
    var current = ""
    var inQuotes = false

    for (i in line.indices) {
      val char = line[i]
      when {
        char == '"' -> inQuotes = !inQuotes
        char == ',' && !inQuotes -> {
          result.add(current.trim())
          current = ""
        }
        else -> current += char
      }
    }
    result.add(current.trim())
    return result
  }

  protected abstract suspend fun processPosition(
    row: Map<String, String>,
    stocks: MutableList<Stock>,
    etfs: MutableList<ETF>,
    cashPositions: MutableList<Cash>
  )
}
