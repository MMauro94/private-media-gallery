package dev.mmauro.privateMediaGallery.common.encryption

import dev.whyoleg.cryptography.algorithms.symmetric.AES
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class VaultKeyTest : FunSpec(
    {
        test("subsequent generate() calls get a different key") {
            val keys = List(10) { VaultKey.generate() }

            keys.map { it.key.encodeTo(AES.Key.Format.RAW) }.distinct().size shouldBe keys.size
        }
    },
)
