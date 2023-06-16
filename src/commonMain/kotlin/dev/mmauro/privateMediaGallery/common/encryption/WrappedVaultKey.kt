package dev.mmauro.privateMediaGallery.common.encryption

import dev.mmauro.privateMediaGallery.common.UserPassword

/**
 * Represents a [VaultKey] that has been wrapped with a [UserPassword].
 *
 * Since the underlying [VaultKey] is encrypted, this can be safely sent and stored in the backend.
 *
 * @see WrappedVaultKey.unwrap
 */
data class WrappedVaultKey(val key: Nothing) {

    /**
     * Unwraps this [WrappedVaultKey] using the given [wrappingKey] and returns the corresponding [VaultKey].
     */
    @Suppress("UNUSED_PARAMETER")
    suspend fun unwrap(wrappingKey: UserKey): VaultKey {
        TODO(
            """
            Key unwrapping is not implemented yet in whyoleg/cryptography-kotlin, will be implemented in 0.2.0
            See: https://github.com/whyoleg/cryptography-kotlin/blob/main/PLANS.md
            """.trimIndent(),
        )
    }
}
