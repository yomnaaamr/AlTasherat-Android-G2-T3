package com.mahmoud.altasherat.common.domain.util

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

sealed interface Resource<out D> {
    data class Success<out D>(val data: D) : Resource<D>
    data class Error(val error: AltasheratError) : Resource<Nothing>
    data object Loading : Resource<Nothing>
}


inline fun <T> Flow<Resource<T>>.onEachSuccessSuspend(crossinline action: suspend (T) -> Unit): Flow<Resource<T>> {
    return onEach { resource ->
        if (resource is Resource.Success) {
            action(resource.data)
        }
    }
}

inline fun <T> Flow<Resource<T>>.onEachErrorSuspend(crossinline action: suspend (AltasheratError) -> Unit): Flow<Resource<T>> {
    return onEach { resource ->
        if (resource is Resource.Error) {
            action(resource.error)
        }
    }
}

inline fun <T> Flow<Resource<T>>.onEachLoadingSuspend(crossinline action: suspend () -> Unit): Flow<Resource<T>> {
    return onEach { resource ->
        if (resource is Resource.Loading) {
            action()
        }
    }
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

