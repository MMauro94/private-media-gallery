package dev.mmauro.privateMediaGallery.server.db

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

fun initTestDb(): ServerDatabase {
    val driver = sqlDriver(JdbcSqliteDriver.IN_MEMORY)
    ServerDatabase.Schema.create(driver)
    return ServerDatabase(driver)
}

fun ServerDatabase.testVault(id: Int, owner: User) = Vault(
    uid = "vault-$id-uuid",
    name = "Test vault $id",
    owner = owner.uid,
).also {
    vaultQueries.insert(it)
}

fun ServerDatabase.testUser(id: Int) = User(
    uid = "user-$id-uuid",
    username = "Test user $id",
    password = List(20) { ('a'..'z').random() }.joinToString(""),
).also {
    userQueries.insert(it)
}
