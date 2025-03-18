package com.mahmoud.altasherat.features.signup.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.common.domain.util.onError
import com.mahmoud.altasherat.features.signup.data.models.request.SignUpRequest
import com.mahmoud.altasherat.features.signup.domain.models.SignUp
import com.mahmoud.altasherat.features.signup.domain.repository.ISignupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SignupUC(
    private val repository: ISignupRepository
) {

    operator fun invoke(signupRequest: SignUpRequest): Flow<Resource<SignUp>> =
        flow {
            emit(Resource.Loading)

            signupRequest.validateSignUpRequest()
                .onError { validationError ->
                    throw AltasheratException(validationError)
                }

            val response = repository.signup(signupRequest)
            emit(Resource.Success(response))

        }.catch { throwable ->
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError(
                        "Unknown error in SignupUC: $throwable"
                    )
                )
            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)
}