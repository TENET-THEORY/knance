package io.tenetinc.knance.exposed

import org.jetbrains.exposed.sql.Database

fun configureDatabase(url: String, user: String, password: String) {
  Database.connect(url = url, user = user, password = password, driver = "org.postgresql.Driver")
}
