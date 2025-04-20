package com.mahmoud.altasherat.features.al_tashirat_services.user.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.IUserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteUserAccessTokenUC(
    private val userRepository: IUserInfoRepository
) {
    operator fun invoke(): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading)
            userRepository.deleteUserAccessToken()
            emit(Resource.Success(Unit))
        }.catch { throwable ->
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError(
                        "Unknown error in DeleteAccessTokenUC: $throwable"
                    )
                )
            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)
}