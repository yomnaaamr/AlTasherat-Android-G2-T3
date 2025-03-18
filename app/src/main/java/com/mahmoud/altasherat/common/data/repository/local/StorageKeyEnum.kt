package com.mahmoud.altasherat.common.data.repository.local

import com.mahmoud.altasherat.common.domain.repository.local.IStorageKeyEnum

enum class StorageKeyEnum(override val keyValue: String) : IStorageKeyEnum {
    COUNTRIES("countries"),
    ACCESS_TOKEN("token"),
    LOGIN("login"),

}