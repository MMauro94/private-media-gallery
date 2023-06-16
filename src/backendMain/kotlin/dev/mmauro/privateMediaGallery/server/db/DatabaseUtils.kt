package dev.mmauro.privateMediaGallery.server.db

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.util.Properties

fun sqlDriver(url: String): JdbcSqliteDriver {
    return JdbcSqliteDriver(
        url,
        properties = Properties().apply { put("foreign_keys", "true") },
    )
}
