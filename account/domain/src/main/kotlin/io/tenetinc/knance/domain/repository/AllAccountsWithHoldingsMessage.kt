package io.tenetinc.knance.domain.repository

import io.tenetinc.knance.domain.model.Account
import io.tenetinc.knance.marketdata.repository.live.BulkQuoteMessage

sealed class AllAccountsWithHoldingsMessage
object AllAccountsWithHoldingsLoadingFromDatabaseMessage : AllAccountsWithHoldingsMessage()
data class AllAccountsWithHoldingsWithoutMarketDataMessage(val accounts: List<Account>) :
  AllAccountsWithHoldingsMessage()
data class AllAccountsWithHoldingsBulkQuoteMessage(val bulkQuoteMessage: BulkQuoteMessage) :
  AllAccountsWithHoldingsMessage()
data class AllAccountsWithMarketDataHoldingsMessage(val accounts: List<Account>) : AllAccountsWithHoldingsMessage()