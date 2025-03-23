package com.mahmoud.altasherat.features.authentication.login.domain.useCases

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.authentication.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.authentication.login.domain.models.Login
import com.mahmoud.altasherat.features.authentication.login.domain.repository.ILoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUC(
    private val loginRepository: ILoginRepository,
) {
    operator fun invoke(loginRequest: LoginRequest): Flow<Resource<Login>> {
        return flow {
            emit(Resource.Loading)

            loginRequest.validateLoginRequest()
                .onSuccess { errors ->
                    if (errors.isNotEmpty())
                        throw AltasheratException(AltasheratError.ValidationErrors(errors))
                }

            val loginResponse: Login = loginRepository.login(loginRequest)
            loginRepository.saveLogin(loginResponse)
            emit(Resource.Success(loginResponse))

        }.catch { throwable ->
            val failureResource =
                if (throwable is AltasheratException) throwable else AltasheratException(
                    AltasheratError.UnknownError(throwable.message!!)
                )
            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)
    }

}