package com.mahmoud.altasherat.features.update_account.data.models.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.util.Constants.EMAIL_PATTERN
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.request.PhoneRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.regex.Pattern


data class UpdateAccRequest(
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("middlename")
    val middlename: String?,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("birth_date")
    val birthDate: String?,
    @SerializedName("phone")
    val phone: PhoneRequest,
    @SerializedName("image")
    val image: File?,
    @SerializedName("country")
    val country: String
) {

    fun validateRequest(): Resource<MutableList<ValidationError>> {
        val errors = mutableListOf<ValidationError>()
        listOf(
            validateFirstName(),
            validateMiddleName(),
            validateLastName(),
            validateEmail(),
            validateImageExtension(),
            validateImageSize(),
            phone.validatePhoneNumberRequest(),
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

    private fun validateMiddleName(): Resource<Unit> {
        if (middlename?.length!! > 15) return Resource.Error(ValidationError.INVALID_MIDDLE_NAME)
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

    private fun validateImageExtension(): Resource<Unit> {
        val allowedExtensions = listOf("jpg", "jpeg", "png")
        if (image != null) {
            val extension = image.extension.lowercase()
            if (extension !in allowedExtensions) {
                return Resource.Error(ValidationError.INVALID_IMAGE_EXTENSION)
            }
        }
        return Resource.Success(Unit)
    }

    private fun validateImageSize(): Resource<Unit> {
        val maxSize = 512 * 1024 // 512 KB
        if (image != null) {
            if (image.length() > maxSize) {
                return Resource.Error(ValidationError.INVALID_IMAGE_SIZE)
            }
        }
        return Resource.Success(Unit)
    }

    fun createPartMap(): Map<String, RequestBody> {
        val map = mutableMapOf<String, RequestBody>()

        // Convert text fields to RequestBody (handling nullable fields)
        this.firstName.takeIf { it.isNotEmpty() }?.let {
            map["firstname"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
        }

        this.middlename?.takeIf { it.isNotEmpty() }?.let {
            map["middlename"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
        }

        this.lastname.takeIf { it.isNotEmpty() }?.let {
            map["lastname"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
        }

        this.email.takeIf { it.isNotEmpty() }?.let {
            map["email"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
        }

        this.birthDate?.takeIf { it.isNotEmpty() }?.let {
            map["birth_date"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
        }

        this.country.takeIf { it.isNotEmpty() }?.let {
            map["country"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
        }

        // Convert PhoneRequest to JSON
        val phoneCountryJson = Gson().toJson(this.phone.countryCode)
        val phoneNumberJson = Gson().toJson(this.phone.number)
        map["number"] = phoneNumberJson.toRequestBody("application/json".toMediaTypeOrNull())
        map["country_code"] = phoneCountryJson.toRequestBody("application/json".toMediaTypeOrNull())

        return map
    }


}