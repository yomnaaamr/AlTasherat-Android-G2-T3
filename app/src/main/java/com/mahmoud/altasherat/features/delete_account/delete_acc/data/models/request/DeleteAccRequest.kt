package com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.request

import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.ValidationError

data class DeleteAccRequest(
    @SerializedName("password")
    val password: String
) {
    fun validateDeleteAccountRequest(): Resource<MutableList<ValidationError>> {

        val errors = mutableListOf<ValidationError>()

        listOf(
            validatePassword(),
        ).forEach { result ->
            if (result is Resource.Error) {
                errors.add(result.error as ValidationError)
            }
        }

        return Resource.Success(errors)
    }

    private fun validatePassword(): Resource<Unit> {
        if (password.isBlank()) return Resource.Error(ValidationError.EMPTY_PASSWORD)
        return Resource.Success(Unit)
    }
}