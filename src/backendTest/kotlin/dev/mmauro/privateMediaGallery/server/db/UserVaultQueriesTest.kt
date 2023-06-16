package dev.mmauro.privateMediaGallery.server.db

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class UserVaultQueriesTest : FunSpec(
    {
        context("insert") {
            val db = initTestDb()

            // Setup test data
            val user1 = db.testUser(1)
            val vault1 = db.testVault(1, user1)
            val vault2 = db.testVault(2, user1)
            val vault3 = db.testVault(3, user1)

            db.userVaultQueries.selectAll().executeAsList().size shouldBe 0

            db.userVaultQueries.insert(user = user1.uid, vault = vault1.uid)
            db.userVaultQueries.insert(user = user1.uid, vault = vault2.uid)
            db.userVaultQueries.insert(user = user1.uid, vault = vault3.uid)

            val inserted = db.userVaultQueries.selectAll().executeAsList()
            inserted.size shouldBe 3
            inserted.map { it.sort } shouldBe listOf(0, 1, 2)
        }
    },
)
