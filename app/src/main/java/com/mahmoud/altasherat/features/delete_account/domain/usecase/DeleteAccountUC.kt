package com.mahmoud.altasherat.features.delete_account.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.delete_account.domain.repository.IDeleteAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteAccountUC(
    private val deleteRepository: IDeleteAccountRepository
) {
    operator fun invoke(password: String): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading)
            val response = deleteRepository.deleteAccount(password)
            emit(Resource.Success(response))
        }.catch { throwable ->
            val failureResource = if (throwable is AltasheratException) throwable else
                AltasheratException(
                    AltasheratError.UnknownError(
                        "Unknown error in DeleteAccountUC: $throwable"
                    )
                )
            emit(Resource.Error(failureResource.error))
        }.flowOn(Dispatchers.IO)
}