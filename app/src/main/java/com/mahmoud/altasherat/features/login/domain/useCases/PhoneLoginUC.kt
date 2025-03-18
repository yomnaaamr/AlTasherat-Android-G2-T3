package com.mahmoud.altasherat.features.login.domain.useCases

import android.util.Log
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.common.util.Constants.TAG
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
            val loginResponse = loginRepository.phoneLogin(loginRequest)
            Log.d(TAG, "loginResponse = $loginResponse ")
            loginRepository.saveLogin(loginResponse)
            emit(Resource.Success(loginResponse))
        }.catch { throwable ->
            if (throwable is AltasheratException) throwable else TODO()
        }.flowOn(Dispatchers.IO)
    }
}