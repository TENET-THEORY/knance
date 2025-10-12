package io.tenetinc.knance.marketdata.marketdata.nymarket

import java.util.Calendar
import java.util.Date
import java.util.TimeZone

const val NY_TIMEZONE_ID = "America/New_York"

fun getMarketOpenTime(): Long {
  val calendar = Calendar.getInstance(TimeZone.getTimeZone(NY_TIMEZONE_ID))
  calendar.time = Date()
  calendar.set(Calendar.HOUR_OF_DAY, 9)
  calendar.set(Calendar.MINUTE, 30)
  calendar.set(Calendar.SECOND, 0)
  calendar.set(Calendar.MILLISECOND, 0)
  return calendar.timeInMillis
}

fun getMarketCloseTime(): Long {
  val calendar = Calendar.getInstance(TimeZone.getTimeZone(NY_TIMEZONE_ID))
  calendar.time = Date()
  calendar.set(Calendar.HOUR_OF_DAY, 16)
  calendar.set(Calendar.MINUTE, 0)
  calendar.set(Calendar.SECOND, 0)
  calendar.set(Calendar.MILLISECOND, 0)
  return calendar.timeInMillis
}

fun getAfterHoursEndTime(): Long {
  val calendar = Calendar.getInstance(TimeZone.getTimeZone(NY_TIMEZONE_ID))
  calendar.time = Date()
  calendar.set(Calendar.HOUR_OF_DAY, 20)
  calendar.set(Calendar.MINUTE, 0)
  calendar.set(Calendar.SECOND, 0)
  calendar.set(Calendar.MILLISECOND, 0)
  return calendar.timeInMillis
}
