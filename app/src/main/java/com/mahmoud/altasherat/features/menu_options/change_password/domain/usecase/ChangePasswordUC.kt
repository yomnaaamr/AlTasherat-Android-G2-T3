package com.mahmoud.altasherat.features.menu_options.change_password.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.menu_options.change_password.data.models.request.ChangePassRequest
import com.mahmoud.altasherat.features.menu_options.change_password.domain.models.ChangePassword
import com.mahmoud.altasherat.features.menu_options.change_password.domain.repository.IChangePassRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ChangePasswordUC(
    private val repository: IChangePassRepository
) {
    operator fun invoke(passwordRequest: ChangePassRequest): Flow<Resource<ChangePassword>> = flow {
        emit(Resource.Loading)
        passwordRequest.validateChangePasswordRequest().onSuccess { errors ->
            if (errors.isNotEmpty()) throw AltasheratException(
                AltasheratError.ValidationErrors(
                    errors
                )
            )
        }
        val response = repository.changePassword(passwordRequest)
        emit(Resource.Success(response))
    }.catch { throwable ->
        val failureResource =
            if (throwable is AltasheratException) throwable else AltasheratException(
                AltasheratError.UnknownError(
                    "Unknown error in ChangePasswordUC: $throwable"
                )
            )
        emit(Resource.Error(failureResource.error))
    }.flowOn(Dispatchers.IO)
}