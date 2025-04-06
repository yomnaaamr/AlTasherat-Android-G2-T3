package com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository.remote

interface IDeleteAccountRemoteDS {
    suspend fun deleteAccount(password: String, token: String): String
}