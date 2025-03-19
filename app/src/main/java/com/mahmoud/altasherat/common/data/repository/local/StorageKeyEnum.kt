package com.mahmoud.altasherat.common.data.repository.local

import com.mahmoud.altasherat.common.domain.repository.local.IStorageKeyEnum

enum class StorageKeyEnum(override val keyValue: String) : IStorageKeyEnum {
    COUNTRIES("countries"),
    SELECTED_LANGUAGE("selected_language"),
    SELECTED_COUNTRY("selected_country"),
    ONBOARDING("onboarding"),
    ACCESS_TOKEN("token"),
    LOGIN("login"),

}