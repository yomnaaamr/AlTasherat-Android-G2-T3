package com.mahmoud.altasherat.features.authentication.login.domain.useCases

import android.util.Log
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.DataInputError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.login.domain.models.Login
import com.mahmoud.altasherat.features.login.domain.repository.ILoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PhoneLoginUC(
    private val loginRepository: ILoginRepository,
) {
    operator fun invoke(loginRequest: LoginRequest): Flow<Resource<Login>> {
        return flow {
            emit(Resource.Loading)
            Log.d("AITASHERAT", "is password valid ?  ${loginRequest.isPasswordValid()} ")
            if (!loginRequest.isPasswordValid()) {
                throw AltasheratException(DataInputError.INVALID_PASSWORD_LENGTH)
            } else {
                Log.d("AITASHERAT", "trying to make phone login request $loginRequest ")
                val loginResponse: Login = loginRepository.phoneLogin(loginRequest)
                Log.d("AITASHERAT", "loginResponse = $loginResponse ")
                loginRepository.saveLogin(loginResponse)
                emit(Resource.Success(loginResponse))
            }
        }.catch { throwable ->
            Log.d(
                "AITASHERAT",
                "catch throwable: ${throwable.message} and cause = ${throwable.cause}  and stactTrach = ${throwable.stackTrace} "
            )
            val failureResource =
                if (throwable is AltasheratException) throwable else AltasheratException(
                    AltasheratError.UnknownError(throwable.message!!)
                )
            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)
    }

}