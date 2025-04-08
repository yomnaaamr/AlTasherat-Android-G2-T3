package com.mahmoud.altasherat.features.menu_options.change_password.data.models.request

import android.util.Log
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
        Log.d("VALIDATION_ERROR_LIST", errors.toString())

        return Resource.Success(errors)
    }

    private fun validateOldPassword(): Resource<Unit> {
        if (oldPassword.isBlank()) return Resource.Error(ValidationError.EMPTY_OLD_PASSWORD)
        return Resource.Success(Unit)
    }

    private fun validateNewPassword(): Resource<Unit> {
        if (newPassword.isBlank()) return Resource.Error(ValidationError.EMPTY_NEW_PASSWORD)
        if (newPassword.length < 8 || newPassword.length > 50) return Resource.Error(ValidationError.INVALID_NEW_PASSWORD)
        return Resource.Success(Unit)
    }

    private fun validateNewPasswordConfirmation(): Resource<Unit> {
        if (newPasswordConfirmation.isBlank()) return Resource.Error(ValidationError.EMPTY_PASSWORD_CONFIRMATION)
        if (newPasswordConfirmation.length < 8 || newPasswordConfirmation.length > 50) return Resource.Error(
            ValidationError.INVALID_PASSWORD_CONFIRMATION
        )
        if (newPasswordConfirmation != newPassword) return Resource.Error(ValidationError.INVALID_PASSWORD_CONFIRMATION)
        return Resource.Success(Unit)
    }

}