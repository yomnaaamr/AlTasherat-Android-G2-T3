package com.mahmoud.altasherat.features.al_tashirat_services.user_services.util

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.request.PhoneRequest
import com.mahmoud.altasherat.features.profile_info.data.models.request.UpdateAccRequest
import okhttp3.RequestBody
import okio.Buffer

fun RequestBody.toStringValue(): String? {
    return try {
        val buffer = Buffer()
        writeTo(buffer)
        buffer.readUtf8()
    } catch (e: Exception) {
        null
    }
}

fun mapToUpdateAccRequest(data: Map<String, RequestBody>): UpdateAccRequest {
    return UpdateAccRequest(
        firstName = data["firstname"]?.toStringValue() ?: "",
        middlename = data["middlename"]?.toStringValue(),
        lastname = data["lastname"]?.toStringValue() ?: "",
        email = data["email"]?.toStringValue() ?: "",
        birthDate = data["birth_date"]?.toStringValue(),
        phone = PhoneRequest(
            data["country_code"]?.toStringValue() ?: "",
            number = data["number"]?.toStringValue() ?: ""
        ),
        country = data["country"]?.toStringValue() ?: "",
        image = null
    )
}
