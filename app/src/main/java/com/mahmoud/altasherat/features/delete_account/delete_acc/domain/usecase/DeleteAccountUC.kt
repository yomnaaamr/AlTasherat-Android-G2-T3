package com.mahmoud.altasherat.features.delete_account.delete_acc.domain.usecase

import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.request.DeleteAccRequest
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.models.DeleteAcc
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository.IDeleteAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteAccountUC(
    private val deleteRepository: IDeleteAccountRepository
) {
    operator fun invoke(request: DeleteAccRequest): Flow<Resource<DeleteAcc>> =
        flow {
            emit(Resource.Loading)
            request.validateDeleteAccountRequest().onSuccess { errors ->
                if (errors.isNotEmpty()) throw AltasheratException(
                    AltasheratError.ValidationErrors(
                        errors
                    )
                )
            }
            val response = deleteRepository.deleteAccount(request)
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