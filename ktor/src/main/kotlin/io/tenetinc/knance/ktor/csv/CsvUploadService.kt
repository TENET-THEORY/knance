package io.tenetinc.knance.ktor.csv

import io.tenetinc.knance.common.api.model.UploadResponse
import io.tenetinc.knance.domain.repository.AccountRepository
import io.tenetinc.knance.ktor.CsvFileData
import io.tenetinc.knance.ktor.ai.FinanceClassifier


class CsvUploadService(
  private val accountRepository: AccountRepository,
  private val financeClassifier: FinanceClassifier
) {

  suspend fun uploadIbkrPositions(csvFileData: CsvFileData): UploadResponse {
    return try {
      val csvProcessor = IbkrCsvProcessor(financeClassifier, csvFileData)
      csvProcessor.previewFileData()
      if (csvProcessor.isValidCsv().not()) {
        return UploadResponse(
          false, "Invalid IBKR CSV format: missing Portfolio or Cash Balances section"
        )
      }
      val assets = csvProcessor.findAssets()
      accountRepository.syncAssets(accountName = "IBKR", assets = assets)
      UploadResponse(true, "IBKR CSV file '${csvFileData.fileName}' processed successfully")
    } catch (e: Exception) {
      UploadResponse(false, "Failed to process IBKR CSV: ${e.message}")
    }
  }

  suspend fun uploadSchwabPositions(csvFileData: CsvFileData): UploadResponse {
    return try {
      val csvProcessor = SchwabCsvProcessor(financeClassifier, csvFileData)
      csvProcessor.previewFileData()
      if (csvProcessor.isValidCsv().not()) {
        return UploadResponse(false, "Empty CSV file")
      }
      val assets = csvProcessor.findAssets()
      accountRepository.syncAssets(accountName = "Schwab", assets = assets)
      UploadResponse(true, "Schwab CSV file '${csvFileData.fileName}' processed successfully")
    } catch (e: Exception) {
      UploadResponse(false, "Failed to process Schwab CSV: ${e.message}")
    }
  }
}
