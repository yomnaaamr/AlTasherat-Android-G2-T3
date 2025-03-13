package com.mahmoud.altasherat.common.domain.util.error

enum class NetworkError : AltasheratError {
    NO_INTERNET,
    SERIALIZATION,
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND
}