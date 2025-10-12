package io.tenetinc.finance.alphavantage.io.tenetinc.knance.client

const val ALPHA_VANTAGE_BASE_URL = "https://www.alphavantage.co/query"

object ParameterKeys {
  const val FUNCTION = "function"
  const val SYMBOL = "symbol"
  const val API_KEY = "apikey"
  const val FROM_CURRENCY = "from_currency"
  const val TO_CURRENCY = "to_currency"
}

object FunctionValues {
  const val REAL_TIME_BULK_QUOTES = "REAL_TIME_BULK_QUOTES"
  const val GLOBAL_QUOTE = "GLOBAL_QUOTE"
  const val CURRENCY_EXCHANGE_RATE = "CURRENCY_EXCHANGE_RATE"
}