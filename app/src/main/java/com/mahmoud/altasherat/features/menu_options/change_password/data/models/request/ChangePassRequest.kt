package com.mahmoud.altasherat.features.menu_options.change_password.data.models.request

import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.ValidationError

data class ChangePassRequest(
    @SerializedName("old_password") val oldPassword: String,
    @SerializedName("new_password") val newPassword: String,
    @SerializedName("new_password_confirmation") val newPasswordConfirmation: String
) {
    fun validateChangePasswordRequest(): Resource<MutableList<ValidationError>> {

        val errors = mutableListOf<ValidationError>()

        listOf(
            validateOldPassword(),
            validateNewPassword(),
            validateNewPasswordConfirmation(),
        ).forEach { result ->
            if (result is Resource.Error) {
                errors.add(result.error as ValidationError)
            }
        }

        return Resource.Success(errors)
    }

    private fun validateOldPassword(): Resource<Unit> {
        if (oldPassword.isNullOrEmpty()) return Resource.Error(ValidationError.EMPTY_PASSWORD)
        return Resource.Success(Unit)
    }

    private fun validateNewPassword(): Resource<Unit> {
        if (newPassword.isNullOrEmpty()) return Resource.Error(ValidationError.EMPTY_PASSWORD)
        if (newPassword.length < 8 || newPassword.length > 50) return Resource.Error(ValidationError.INVALID_PASSWORD)
        return Resource.Success(Unit)
    }

    private fun validateNewPasswordConfirmation(): Resource<Unit> {
        if (newPasswordConfirmation.isNullOrEmpty()) return Resource.Error(ValidationError.EMPTY_PASSWORD)
        if (newPasswordConfirmation.length < 8 || newPasswordConfirmation.length > 50) return Resource.Error(
            ValidationError.INVALID_PASSWORD
        )
        if (newPasswordConfirmation != newPassword) return Resource.Error(ValidationError.INVALID_PASSWORD_CONFIRMATION)
        return Resource.Success(Unit)
    }

}