package com.mahmoud.altasherat.common.presentation.utils

import android.content.Context
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
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
    }
}


fun LocalStorageError.toResourceId(): Int {
    return when (this) {
        LocalStorageError.IO_ERROR -> R.string.error_io
        LocalStorageError.DATA_CORRUPTION -> R.string.error_data_corruption
        LocalStorageError.TYPE_MISMATCH -> R.string.error_type_mismatch
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

        else -> context.getString(R.string.error_unknown)
    }
}