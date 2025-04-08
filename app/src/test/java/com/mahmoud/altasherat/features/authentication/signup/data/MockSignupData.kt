package com.mahmoud.altasherat.features.authentication.signup.data

import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.UserDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.request.PhoneRequest
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.Image
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.Phone
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User
import com.mahmoud.altasherat.features.authentication.signup.data.models.dto.SignUpResponseDto
import com.mahmoud.altasherat.features.authentication.signup.data.models.request.SignUpRequest
import com.mahmoud.altasherat.features.authentication.signup.domain.models.SignUp

internal object MockSignupData {

    private val validPhoneRequest = PhoneRequest("+20", "1234567890")
    val validSignUpRequest = SignUpRequest(
        firstName = "John",
        lastname = "Doe",
        email = "john.doe@example.com",
        password = "password123",
        passwordConfirmation = "password123",
        phone = validPhoneRequest,
        country = "eg"
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

    val validSignUpDto = SignUpResponseDto(
        message = "Signup successful",
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

    val validSignUp = SignUp(
        message = "Signup successful",
        token = "test_token",
        user = validUser
    )


    val invalidSignUpRequestEmptyFirstName = validSignUpRequest.copy(firstName = "")
    val invalidSignUpRequestShortFirstName = validSignUpRequest.copy(firstName = "Jo")
    val invalidSignUpRequestEmptyLastName = validSignUpRequest.copy(lastname = "")
    val invalidSignUpRequestShortLastName = validSignUpRequest.copy(lastname = "Do")
    val invalidSignUpRequestEmptyEmail = validSignUpRequest.copy(email = "")
    val invalidSignUpRequestInvalidEmail = validSignUpRequest.copy(email = "invalidemail")
    val invalidSignUpRequestEmptyPassword = validSignUpRequest.copy(password = "")
    val invalidSignUpRequestShortPassword = validSignUpRequest.copy(password = "pass")
    val invalidSignUpRequestEmptyPhoneNumber =
        validSignUpRequest.copy(phone = validPhoneRequest.copy(number = ""))
    val invalidSignUpRequestShortPhoneNumber =
        validSignUpRequest.copy(phone = validPhoneRequest.copy(number = "123"))
    val invalidSignUpRequestEmptyCountryCode =
        validSignUpRequest.copy(phone = validPhoneRequest.copy(countryCode = ""))
    val invalidSignUpRequestShortCountryCode =
        validSignUpRequest.copy(phone = validPhoneRequest.copy(countryCode = "1"))

}