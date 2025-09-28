package io.tenetinc.knance.exposed.db

import io.tenetinc.knance.domain.model.AssetType
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.postgresql.util.PGobject

internal object AccountTable : IntIdTable("account") {
  val name = varchar("name", 100)
}

internal object CashTable : IntIdTable("cash") {
  val amount = float("amount")
  val currency = varchar("currency", 3)
  val accountId = reference("account_id", AccountTable)
}

internal object StockTable : IntIdTable("stock") {
  val symbol = varchar("symbol", 10)
  val quantity = float("quantity")
  val costBasis = float("cost_basis")
  val accountId = reference("account_id", AccountTable)
}

internal object ETFTable : IntIdTable("etf") {
  val symbol = varchar("symbol", 10)
  val quantity = float("quantity")
  val costBasis = float("cost_basis")
  val accountId = reference("account_id", AccountTable)
  val assetType =
      customEnumeration(
          name = "asset_type",
          sql = "assettype",
          fromDb = { value -> AssetType.valueOf(value as String) },
          toDb = { enum -> PGEnum("assettype", enum) })
}

class PGEnum<T : Enum<T>>(enumTypeName: String, enumValue: T?) : PGobject() {
  init {
    value = enumValue?.name
    type = enumTypeName
  }
}

internal object CreditCardTable : IntIdTable("credit_card") {
  val balance = float("balance")
  val accountId = reference("account_id", AccountTable)
}

internal class AccountDAO(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<AccountDAO>(AccountTable)

  var name by AccountTable.name
  val cashHoldings by CashDAO referrersOn CashTable.accountId
  val stockHoldings by StockDAO referrersOn StockTable.accountId
  val etfHoldings by ETFDAO referrersOn ETFTable.accountId
  val creditCards by CreditCardDAO referrersOn CreditCardTable.accountId
}

internal class CashDAO(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<CashDAO>(CashTable)

  var amount by CashTable.amount
  var currency by CashTable.currency
  var account by AccountDAO referencedOn CashTable.accountId
}

internal class StockDAO(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<StockDAO>(StockTable)

  var symbol by StockTable.symbol
  var quantity by StockTable.quantity
  var costBasis by StockTable.costBasis
  var account by AccountDAO referencedOn StockTable.accountId
}

internal class ETFDAO(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<ETFDAO>(ETFTable)

  var symbol by ETFTable.symbol
  var quantity by ETFTable.quantity
  var costBasis by ETFTable.costBasis
  var account by AccountDAO referencedOn ETFTable.accountId
  var assetType by ETFTable.assetType
}

internal class CreditCardDAO(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<CreditCardDAO>(CreditCardTable)

  var balance by CreditCardTable.balance
  var account by AccountDAO referencedOn CreditCardTable.accountId
}

internal suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)
