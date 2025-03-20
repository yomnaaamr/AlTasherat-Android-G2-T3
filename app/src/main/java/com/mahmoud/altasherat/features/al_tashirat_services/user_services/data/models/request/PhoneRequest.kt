package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.request

import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.ValidationError

data class PhoneRequest(
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("number")
    val number: String,
){

    fun validatePhoneNumberRequest(): Resource<Unit> {
        validatePhoneNumber().let { if (it is Resource.Error) return it }
        validatePhoneCountryCode().let { if (it is Resource.Error) return it }
        return Resource.Success(Unit)
    }

    private fun validatePhoneNumber(): Resource<Unit> {
        if (number.isBlank()) return Resource.Error(ValidationError.EMPTY_PHONE_NUMBER)
        if (number.length < 9 || number.length > 15) return Resource.Error(ValidationError.INVALID_PHONE_NUMBER)
        return Resource.Success(Unit)
    }

    private fun validatePhoneCountryCode(): Resource<Unit> {
        if (countryCode.isBlank()) return Resource.Error(ValidationError.EMPTY_COUNTRY_CODE)
        if (countryCode.length < 3 || countryCode.length > 5) return Resource.Error(ValidationError.INVALID_COUNTRY_CODE)
        return Resource.Success(Unit)
    }
}