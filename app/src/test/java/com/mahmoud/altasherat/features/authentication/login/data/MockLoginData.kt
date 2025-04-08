package com.mahmoud.altasherat.features.authentication.login.data

import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.UserDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.request.PhoneRequest
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.Image
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.Phone
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User
import com.mahmoud.altasherat.features.authentication.login.data.models.dto.LoginResponseDto
import com.mahmoud.altasherat.features.authentication.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.authentication.login.domain.models.Login

internal object MockLoginData {

    private val validPhoneRequest = PhoneRequest("+20", "1234567890")
    val validLoginRequest = LoginRequest(
        phone = validPhoneRequest,
        password = "password123"
    )

    private val validPhoneDto = PhoneDto(
        countryCode = "+20",
        number = "1234567890",
        id = 1,
        extension = "123",
        type = "mobile",
        holderName = "John Doe"
    )

    private val validUserDto = UserDto(
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

    val validLoginDto = LoginResponseDto(
        message = "Login successful",
        token = "test_token",
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

    val validLogin = Login(
        message = "Login successful",
        token = "test_token",
        user = validUser
    )

    val invalidLoginRequestEmptyPassword = validLoginRequest.copy(password = "")
    val invalidLoginRequestShortPassword = validLoginRequest.copy(password = "pass")
    val invalidLoginRequestEmptyPhoneNumber =
        validLoginRequest.copy(phone = validPhoneRequest.copy(number = ""))
    val invalidLoginRequestShortPhoneNumber =
        validLoginRequest.copy(phone = validPhoneRequest.copy(number = "123"))
    val invalidLoginRequestEmptyCountryCode =
        validLoginRequest.copy(phone = validPhoneRequest.copy(countryCode = ""))
    val invalidLoginRequestShortCountryCode =
        validLoginRequest.copy(phone = validPhoneRequest.copy(countryCode = "1"))
}