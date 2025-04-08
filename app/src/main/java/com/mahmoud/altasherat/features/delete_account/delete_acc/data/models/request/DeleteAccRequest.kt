package com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.request

import com.google.gson.annotations.SerializedName

data class DeleteAccRequest(
    @SerializedName("password")
    val password: String
)