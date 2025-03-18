package com.mahmoud.altasherat.features.login.data.models.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("phone[country_code]")
    val countryCode: String,
    @SerialName("phone[number]")
    val phone: String,
    val password: String,
) {
    fun validatePassword(): Boolean {
        return !(password.isBlank() || password.length < 8 || password.length > 50)
    }

}
