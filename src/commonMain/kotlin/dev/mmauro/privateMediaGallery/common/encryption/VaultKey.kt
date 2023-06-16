package dev.mmauro.privateMediaGallery.common.encryption

import dev.whyoleg.cryptography.algorithms.symmetric.AES
import dev.whyoleg.cryptography.algorithms.symmetric.SymmetricKeySize
import dev.whyoleg.cryptography.provider.CryptographyProvider

/**
 * Represents the private key of a vault.
 *
 * This key has to be used to encrypt media for a vault.
 *
 * **This key should NEVER be sent to the backend without being encrypted (wrapped) first!**
 *
 * @see VaultKey.wrap
 */
data class VaultKey(val key: AES.GCM.Key) {

    /**
     * Wraps this [VaultKey] using the provided user [wrappingKey].
     */
    @Suppress("UNUSED_PARAMETER")
    fun wrap(wrappingKey: UserKey): WrappedVaultKey {
        TODO(
            """
            Key wrapping is not implemented yet in whyoleg/cryptography-kotlin, will be implemented in 0.2.0
            See: https://github.com/whyoleg/cryptography-kotlin/blob/main/PLANS.md
            """.trimIndent(),
        )
    }

    companion object {

        private val KEY_ALGORITHM = AES.GCM
        private val KEY_SIZE = SymmetricKeySize.B256

        /**
         * Generates a new secure and random [VaultKey].
         */
        suspend fun generate(): VaultKey {
            return CryptographyProvider.Default
                .get(KEY_ALGORITHM)
                .keyGenerator(KEY_SIZE)
                .generateKey()
                .let { VaultKey(it) }
        }
    }
}
