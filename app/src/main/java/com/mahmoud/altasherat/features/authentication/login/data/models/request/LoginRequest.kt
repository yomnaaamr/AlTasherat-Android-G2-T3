package com.mahmoud.altasherat.features.authentication.login.data.models.request

import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.request.PhoneRequest


data class LoginRequest(
    @SerializedName("phone")
    val phone: PhoneRequest,
    val password: String,
) {

    fun validateLoginRequest(): Resource<MutableList<ValidationError>> {

        val errors = mutableListOf<ValidationError>()

        listOf(
            isPasswordValid(),
            phone.validatePhoneNumberRequest(),
        ).forEach { result ->
            if (result is Resource.Error) {
                errors.add(result.error as ValidationError)
            }
        }

        return Resource.Success(errors)
    }



    private fun isPasswordValid(): Resource<Unit> {
        if (password.isBlank()) return Resource.Error(ValidationError.EMPTY_PASSWORD)
        if (password.length < 8 || password.length > 50) return Resource.Error(ValidationError.INVALID_PASSWORD)

        return Resource.Success(Unit)
    }


}
