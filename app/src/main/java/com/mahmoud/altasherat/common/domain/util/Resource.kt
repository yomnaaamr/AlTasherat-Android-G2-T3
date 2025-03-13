package com.mahmoud.altasherat.common.domain.util

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError

sealed interface Resource<out D> {
    data class Success<out D>(val data: D) : Resource<D>
    data class Error(val error: AltasheratError) : Resource<Nothing>
    data object Loading : Resource<Nothing>
}


inline fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    return when (this) {
        is Resource.Error -> this
        is Resource.Success -> {
            action(data)
            this
        }

        is Resource.Loading -> this
    }
}

inline fun <T> Resource<T>.onError(action: (AltasheratError) -> Unit): Resource<T> {
    return when (this) {
        is Resource.Error -> {
            action(error)
            this
        }

        is Resource.Success -> this
        is Resource.Loading -> this
    }
}

