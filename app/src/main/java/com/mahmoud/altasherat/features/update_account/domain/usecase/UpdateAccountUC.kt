package com.mahmoud.altasherat.features.update_account.domain.usecase

import android.util.Log
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.update_account.data.models.request.UpdateAccRequest
import com.mahmoud.altasherat.features.update_account.domain.models.UpdateAcc
import com.mahmoud.altasherat.features.update_account.domain.repository.IUpdateAccRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdateAccountUC(
    private val repository: IUpdateAccRepository
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
            Log.d("USECASE_RESPONSE", response.toString())
            repository.updateLocalUserInfo(response)
            emit(Resource.Success(response))

        }.catch { throwable ->
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError(
                        "Unknown error in UpdateUserInfoUC: $throwable"
                    )
                )
            Log.d("USECASE_RESPONSE", failureResource.error.toString())

            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)

}