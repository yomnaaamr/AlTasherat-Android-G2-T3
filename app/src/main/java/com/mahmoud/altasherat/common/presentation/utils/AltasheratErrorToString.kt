package com.mahmoud.altasherat.common.presentation.utils

import android.content.Context
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.DataInputError
import com.mahmoud.altasherat.common.domain.util.error.LocalStorageError
import com.mahmoud.altasherat.common.domain.util.error.NetworkError

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
fun DataInputError.toErrorMessage(context: Context):String{
    return when(this){
        DataInputError.INVALID_PASSWORD_LENGTH -> context.getString(R.string.invalid_password_length)
        DataInputError.INVALID_PHONE_NUMBER -> TODO()
    }
}

fun AltasheratError.toErrorMessage(context: Context): String {
    return when (this) {
        is NetworkError -> context.getString(this.toResourceId())
        is LocalStorageError -> context.getString(this.toResourceId())
        is DataInputError -> this.toErrorMessage(context)
        is AltasheratError.UnknownError -> this.unknownErrorMessage
        is AltasheratError.UnknownServerError -> context.getString(
            R.string.error_unknown_http,
            this.code
        )

        else -> context.getString(R.string.error_unknown)
    }
}