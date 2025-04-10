package com.mahmoud.altasherat.features.al_tashirat_services.user.data

import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.entity.ImageEntity
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.entity.PhoneEntity
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.entity.UserEntity

internal object MockUserData {
    val userResponse = UserEntity(
        id = 123,
        email = "john@example.com",
        username = "john123",
        firstname = "John",
        middlename = "Albert",
        lastname = "Doe",
        phone = PhoneEntity(
            id = 10,
            countryCode = "0020",
            number = "10222342199",
            extension = "",
            type = "",
            holderName = ""
        ),
        image = ImageEntity(
            id = 12,
            type = "photo",
            path = "http://127.0.0.1:8000/storage/photos/users/yahia12141.png",
            title = "yahia12141 image",
            description = "",
            priority = 0,
            main = false,
            createdAt = "",
            updatedAt = ""
        ),
        birthDate = "1-10-2001",
        emailVerified = false,
        phoneVerified = false,
        isBlocked = 0
    )

}