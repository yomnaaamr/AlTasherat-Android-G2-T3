package com.mahmoud.altasherat.features.authentication.login.data.models.request

import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.request.PhoneRequest
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerializedName("phone") val phone: PhoneRequest,
    val password: String,
) {
    fun isPasswordValid(): Boolean {
        return password.isNotBlank() && password.length in 8..50
    }


}
