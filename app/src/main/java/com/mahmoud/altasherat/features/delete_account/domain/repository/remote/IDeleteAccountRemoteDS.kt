package com.mahmoud.altasherat.features.delete_account.domain.repository.remote

interface IDeleteAccountRemoteDS {
    suspend fun deleteAccount(password: String, token: String): String
}