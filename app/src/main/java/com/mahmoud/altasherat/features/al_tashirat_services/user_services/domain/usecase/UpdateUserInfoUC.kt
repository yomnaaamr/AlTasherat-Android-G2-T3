package com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.usecase

import android.util.Log
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.IUserInfoRepository
import com.mahmoud.altasherat.features.profile_info.data.models.request.UpdateAccRequest
import com.mahmoud.altasherat.features.profile_info.domain.models.UpdateAcc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdateUserInfoUC(
    private val repository: IUserInfoRepository
) {
    operator fun invoke(updateAccRequest: UpdateAccRequest): Flow<Resource<UpdateAcc>> =
        flow {
            emit(Resource.Loading)

            updateAccRequest.validateRequest()
                .onSuccess { errors ->
                    if (errors.isNotEmpty()) {
                        Log.e("VALIDATION_ERRORS", errors.toString())
                        throw AltasheratException(AltasheratError.ValidationErrors(errors))
                    }
                }

            val response = repository.updateRemoteUserInfo(updateAccRequest)
            repository.updateLocalUserInfo(response)
            emit(Resource.Success(response))

        }.catch { throwable ->
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError(
                        "Unknown error in UpdateUserInfoUC: $throwable"
                    )
                )
            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)

}