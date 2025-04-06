package com.mahmoud.altasherat.features.delete_account.domain.repository

interface IDeleteAccountRepository {
    suspend fun deleteAccount(password: String): String
}