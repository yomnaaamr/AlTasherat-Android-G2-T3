package com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.repository.IUserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetUserInfoUC(
    private val userRepository: IUserInfoRepository
) {
    suspend operator fun invoke(): Flow<Resource<User>> =
        flow {
            emit(Resource.Loading)
            val userResponse = userRepository.getUserInfo()
            emit(Resource.Success(userResponse))
        }.catch { throwable ->
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError(
                        "Unknown error in GetUserInfoUC: $throwable"
                    )
                )
            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)

}