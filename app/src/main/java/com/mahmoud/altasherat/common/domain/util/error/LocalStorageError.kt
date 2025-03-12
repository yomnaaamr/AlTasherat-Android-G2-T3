package com.mahmoud.altasherat.common.domain.util.error

enum class LocalStorageError : AltasheratError {
    IO_ERROR,
    DATA_CORRUPTION,
    TYPE_MISMATCH,
}