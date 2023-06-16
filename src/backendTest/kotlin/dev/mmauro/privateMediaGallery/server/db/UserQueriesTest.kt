package dev.mmauro.privateMediaGallery.server.db

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class UserQueriesTest : FunSpec(
    {
        isolationMode = IsolationMode.InstancePerLeaf

        context("vaults") {
            val db = initTestDb()

            // Setup test data
            val user1 = db.testUser(1)
            val vault1 = db.testVault(1, user1)
            val vault2 = db.testVault(2, user1)
            val vault3 = db.testVault(3, user1)

            // Some more data
            val user2 = db.testUser(2)
            val vault4 = db.testVault(4, user2)
            db.userVaultQueries.insert(user = user2.uid, vault = vault4.uid)

            test("owned vault is returned even if not present in UserVault table") {
                db.userQueries.vaults(user = user1.uid).executeAsList().toSet() shouldBe setOf(vault1, vault2, vault3)
            }

            test("vaults returned in correct order with mixed owned and UserVault results") {
                db.userVaultQueries.insert(user = user1.uid, vault = vault2.uid)
                db.userVaultQueries.insert(user = user1.uid, vault = vault1.uid)

                // Before we get the things NOT in the UserVault table, later we get what is in the UserVault sorted
                db.userQueries.vaults(user = user1.uid).executeAsList() shouldBe listOf(vault3, vault2, vault1)
            }
        }
    },
)
