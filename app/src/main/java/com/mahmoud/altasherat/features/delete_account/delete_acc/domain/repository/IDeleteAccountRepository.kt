package com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository

interface IDeleteAccountRepository {
    suspend fun deleteAccount(password: String): String
}