package com.mahmoud.altasherat.common.domain.util.error

enum class ValidationError : AltasheratError {
    INVALID_EMAIL,
    EMPTY_EMAIL,
    INVALID_PASSWORD,
    EMPTY_PASSWORD,
    EMPTY_FIRSTNAME,
    INVALID_FIRSTNAME,
    EMPTY_LASTNAME,
    INVALID_LASTNAME,
    EMPTY_PHONE_NUMBER,
    INVALID_PHONE_NUMBER,
    EMPTY_COUNTRY_CODE,
    INVALID_COUNTRY_CODE
}