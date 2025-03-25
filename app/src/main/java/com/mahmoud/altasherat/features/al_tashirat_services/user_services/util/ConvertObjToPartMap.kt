package com.mahmoud.altasherat.features.al_tashirat_services.user_services.util

import com.google.gson.Gson
import com.mahmoud.altasherat.features.profile_info.data.models.request.UpdateAccRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun createPartMap(request: UpdateAccRequest): Map<String, RequestBody> {
    val map = mutableMapOf<String, RequestBody>()

    // Convert text fields to RequestBody (handling nullable fields)
    request.firstName.takeIf { it.isNotEmpty() }?.let {
        map["firstname"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    request.middlename?.takeIf { it.isNotEmpty() }?.let {
        map["middlename"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    request.lastname.takeIf { it.isNotEmpty() }?.let {
        map["lastname"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    request.email.takeIf { it.isNotEmpty() }?.let {
        map["email"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    request.birthDate?.takeIf { it.isNotEmpty() }?.let {
        map["birth_date"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    request.country.takeIf { it.isNotEmpty() }?.let {
        map["country"] = it.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    // Convert PhoneRequest to JSON
    val phoneJson = Gson().toJson(request.phone)
    map["phone"] = phoneJson.toRequestBody("application/json".toMediaTypeOrNull())

    return map
}