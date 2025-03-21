package com.mahmoud.altasherat.common.presentation.utils

import android.content.Context
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.LocalStorageError
import com.mahmoud.altasherat.common.domain.util.error.NetworkError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError

fun NetworkError.toResourceId(): Int {
    return when (this) {
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERIALIZATION -> R.string.error_serialization
        NetworkError.BAD_REQUEST -> R.string.error_bad_request
        NetworkError.UNAUTHORIZED -> R.string.error_unauthorized
        NetworkError.FORBIDDEN -> R.string.error_forbidden
        NetworkError.NOT_FOUND -> R.string.error_not_found
        NetworkError.InvalidCredentials -> R.string.invalid_phone_or_password
    }
}


fun LocalStorageError.toResourceId(): Int {
    return when (this) {
        LocalStorageError.IO_ERROR -> R.string.error_io
        LocalStorageError.DATA_CORRUPTION -> R.string.error_data_corruption
        LocalStorageError.TYPE_MISMATCH -> R.string.error_type_mismatch
    }
}


fun ValidationError.toResourceId(): Int {
    return when (this) {
        ValidationError.INVALID_EMAIL -> R.string.error_invalid_email
        ValidationError.EMPTY_EMAIL -> R.string.error_empty_email
        ValidationError.INVALID_PASSWORD -> R.string.error_invalid_password
        ValidationError.EMPTY_PASSWORD -> R.string.error_empty_password
        ValidationError.EMPTY_FIRSTNAME -> R.string.error_empty_firstname
        ValidationError.INVALID_FIRSTNAME -> R.string.error_invalid_firstname
        ValidationError.EMPTY_LASTNAME -> R.string.error_empty_lastname
        ValidationError.INVALID_LASTNAME -> R.string.error_invalid_lastname
        ValidationError.EMPTY_PHONE_NUMBER -> R.string.error_empty_phone_number
        ValidationError.INVALID_PHONE_NUMBER -> R.string.error_invalid_phone_number
        ValidationError.EMPTY_COUNTRY_CODE -> R.string.error_empty_country_code
        ValidationError.INVALID_COUNTRY_CODE -> R.string.error_invalid_country_code
    }
}

fun AltasheratError.toErrorMessage(context: Context): String {
    return when (this) {
        is NetworkError -> context.getString(this.toResourceId())
        is LocalStorageError -> context.getString(this.toResourceId())
        is AltasheratError.UnknownError -> this.unknownErrorMessage
        is AltasheratError.UnknownServerError -> context.getString(
            R.string.error_unknown_http,
            this.code
        )
        is ValidationError -> context.getString(this.toResourceId())
        else -> context.getString(R.string.error_unknown)
    }
}