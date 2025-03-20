package com.mahmoud.altasherat.features.authentication.signup.data.repository.local

import com.google.gson.Gson
import com.mahmoud.altasherat.common.data.repository.local.StorageKeyEnum
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.features.authentication.signup.data.models.entity.SignUpEntity
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.local.ISignupLocalDS

internal class SignupLocalDS(
    private val localStorageProvider: ILocalStorageProvider,
    private val gson: Gson
) : ISignupLocalDS {
    override suspend fun saveSignup(signupEntity: SignUpEntity) {
        localStorageProvider.save(
            StorageKeyEnum.ACCESS_TOKEN,
            signupEntity.token,
            String::class
        )
        val userJson = gson.toJson(signupEntity.user)
        localStorageProvider.save(StorageKeyEnum.USER, userJson, String::class)
    }
}