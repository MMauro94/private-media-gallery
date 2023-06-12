package dev.mmauro.privateMediaGallery.common.encryption

import dev.mmauro.privateMediaGallery.common.UserPassword

/**
 * Key that is derived from a [UserPassword] and that has to be used as a _wrapping key_ to encrypt a [VaultKey].
 */
data class UserKey(val key: Nothing)
