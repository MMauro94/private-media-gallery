package dev.mmauro.privateMediaGallery.common

import dev.mmauro.privateMediaGallery.common.encryption.UserKey
import dev.mmauro.privateMediaGallery.common.encryption.VaultKey

/**
 * Represents a user's password.
 */
data class UserPassword(val password: String) {

    /**
     * Generates a new [UserKey] by using this [UserPassword].
     *
     * The generated [UserKey] can then be used to encrypt/decrypt (wrap/unwrap) a [VaultKey].
     *
     * @see VaultKey.wrap
     */
    suspend fun deriveKey(): UserKey {
        TODO(
            """
            Key derivation is not implemented yet in whyoleg/cryptography-kotlin, will be implemented in 0.2.0
            See: https://github.com/whyoleg/cryptography-kotlin/blob/main/PLANS.md
            """.trimIndent(),
        )
    }
}
