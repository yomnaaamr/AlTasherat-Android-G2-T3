package com.mahmoud.altasherat.features.login.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



data class Phone(
    val id: Int,
    val countryCode: String?= null,
    val extension: Any? = null,
    val holderName: String? = null,
    val number: String,
    val type: String
)