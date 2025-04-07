package com.mahmoud.altasherat.features.al_tashirat_services.user.util

import com.mahmoud.altasherat.features.update_account.data.models.request.UpdateAccRequest
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
        countryCode = data["country_code"]?.toStringValue() ?: "",
        number = data["number"]?.toStringValue() ?: "",
        country = data["country"]?.toStringValue() ?: "",
        image = null
    )
}
