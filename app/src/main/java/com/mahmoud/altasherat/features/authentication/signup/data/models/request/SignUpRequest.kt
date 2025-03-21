package com.mahmoud.altasherat.features.authentication.signup.data.models.request

import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.request.PhoneRequest
import java.util.regex.Pattern

private const val EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"


data class SignUpRequest(
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String,
    @SerializedName("phone")
    val phone: PhoneRequest,
    @SerializedName("country")
    val country: String
) {

    fun validateSignUpRequest(): Resource<MutableList<ValidationError>> {

        val errors = mutableListOf<ValidationError>()

        listOf(
            validateFirstName(),
            validateLastName(),
            validateEmail(),
            phone.validatePhoneNumberRequest(),
            validatePassword()
        ).forEach { result ->
            if (result is Resource.Error) {
                errors.add(result.error as ValidationError)
            }
        }

        return Resource.Success(errors)
    }



    private fun validateFirstName(): Resource<Unit> {
        if (firstName.isBlank()) return Resource.Error(ValidationError.EMPTY_FIRSTNAME)
        if (firstName.length < 3 || firstName.length > 15) return Resource.Error(ValidationError.INVALID_FIRSTNAME)
        return Resource.Success(Unit)
    }


    private fun validateLastName(): Resource<Unit> {
        if (lastname.isBlank()) return Resource.Error(ValidationError.EMPTY_LASTNAME)
        if (lastname.length < 3 || lastname.length > 15) return Resource.Error(ValidationError.INVALID_LASTNAME)
        return Resource.Success(Unit)
    }

    private fun validateEmail(): Resource<Unit> {
        if (email.isBlank()) return Resource.Error(ValidationError.EMPTY_EMAIL)
        if (!Pattern.compile(EMAIL_PATTERN).matcher(email)
                .matches()
        ) return Resource.Error(ValidationError.INVALID_EMAIL)
        if (email.length > 50) return Resource.Error(ValidationError.INVALID_EMAIL)
        return Resource.Success(Unit)
    }

    private fun validatePassword(): Resource<Unit> {
        if (password.isBlank()) return Resource.Error(ValidationError.EMPTY_PASSWORD)
        if (password.length < 8 || password.length > 50) return Resource.Error(ValidationError.INVALID_PASSWORD)
        return Resource.Success(Unit)
    }

}

data class ValidationResult(
    val errors: Map<String, AltasheratError> = emptyMap(),
    val isValid: Boolean = errors.isEmpty()
)