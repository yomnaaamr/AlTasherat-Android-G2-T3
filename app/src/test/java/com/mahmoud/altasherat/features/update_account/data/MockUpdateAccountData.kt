package com.mahmoud.altasherat.features.update_account.data

import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.UserDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.Image
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.Phone
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User
import com.mahmoud.altasherat.features.update_account.data.models.dto.UpdateAccDto
import com.mahmoud.altasherat.features.update_account.data.models.request.UpdateAccRequest
import com.mahmoud.altasherat.features.update_account.domain.models.UpdateAcc

internal object MockUpdateAccountData {
    val validUpdateAccRequest = UpdateAccRequest(
        firstName = "John",
        middlename = "",
        lastname = "Doe",
        email = "john.doe@example.com",
        birthDate = "",
        countryCode = "+20",
        number = "1234567890",
        image = null,
        country = "2"
    )

    private val validPhoneDto = PhoneDto(
        countryCode = "+20",
        number = "1234567890",
        id = 1,
        extension = "123",
        type = "mobile",
        holderName = "John Doe"

    )

    val validUserDto = UserDto(
        id = 1,
        userName = "johndoe",
        firstName = "John",
        middleName = null,
        lastName = "Doe",
        email = "john.doe@example.com",
        phone = validPhoneDto,
        image = null,
        birthDate = null,
        emailVerified = false,
        phoneVerified = false,
        isBlocked = 0
    )
    
    val validUpdateAccDto = UpdateAccDto(
        message = "Updated successfully",
        user = validUserDto
    )

    private val validPhone = Phone(
        countryCode = "+20",
        number = "1234567890",
        id = 1,
        extension = "123",
        type = "mobile",
        holderName = "John Doe"
    )

    private val validUser = User(
        id = 1,
        middleName = "",
        username = "johndoe",
        firstname = "John",
        lastname = "Doe",
        email = "john.doe@example.com",
        phone = validPhone,
        image = Image(
            id = -1,
            type = "",
            path = "",
            title = "",
            description = "",
            priority = -1,
            main = false,
            createdAt = "",
            updatedAt = ""
        ),
        birthDate = "",
        emailVerified = false,
        phoneVerified = false,
        isBlocked = 0
    )

    val validUpdateAcc = UpdateAcc(
        message = "Updated successfully",
        user = validUser
    )
}