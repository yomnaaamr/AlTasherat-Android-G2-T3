package com.mahmoud.altasherat.features.tourism_visa.data

import com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.dto.CountryDto
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.ImageDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.UserDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.Image
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.Phone
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User
import com.mahmoud.altasherat.features.tourism_visa.data.models.dto.TourismVisaDto
import com.mahmoud.altasherat.features.tourism_visa.data.models.dto.TourismVisaResponseDto
import com.mahmoud.altasherat.features.tourism_visa.data.models.request.StoreTourismVisaRequest
import com.mahmoud.altasherat.features.tourism_visa.domain.models.TourismVisa
import com.mahmoud.altasherat.features.tourism_visa.domain.models.TourismVisaResponse
import java.io.File


internal object MockTourismVisaData {
    val validTourismVisaRequest = StoreTourismVisaRequest(
        firstName = "John",
        middleName = "Andro",
        lastname = "Doe",
        gender = 1,
        birthDate = "1990-01-01",
        passportNumber = "121234561234567",
        passportImages = listOf(File("passport.jpg")),
        attachments = listOf(File("attachment.pdf")),
        countryCode = "0020",
        number = "1234567890",
        email = "john.doe@example.com",
        destinationCountry = 100,
        purposeOfVisit = "Tourism",
        adultsCount = 2,
        childrenCount = 1,
        message = "Looking forward to the trip"
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

    val validCountryDto = CountryDto(
        id = 1,
        name = "Egypt",
        currency = "EGP",
        code = "EG",
        phoneCode = "0020",
        flag = "flag1"
    )
    
    val validImageDto = ImageDto(
        id = 12,
        type = "",
        path = "",
        title = "",
        description = "",
        priority = 0,
        main = false,
        createdAt = "",
        updatedAt = ""
    )

    val validTourismVisaDto = TourismVisaDto(
        id = 1,
        user = validUserDto,
        firstName = "John",
        middleName = "A.",
        lastName = "Doe",
        gender = 1,
        birthdate = "1990-01-01",
        passportNumber = "X1234567",
        passportImages = listOf(validImageDto),
        attachments = listOf(validImageDto),
        destinationCountry = validCountryDto,
        purposeOfVisit = "Tourism",
        adultsCount = 2,
        childrenCount = 1,
        message = "Looking forward to the trip",
        nationality = validCountryDto,
        phone = validPhoneDto,
        contactEmail = "test@gmail.com",
        statuses = emptyList(),
    )

    val validTourismVisaResponseDto = TourismVisaResponseDto(
        message = "Store Tourism Visa Successfully",
        tourismVisa = validTourismVisaDto,
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

    val validCountry = Country(
        id = 1,
        name = "Egypt",
        currency = "EGP",
        code = "EG",
        phoneCode = "0020",
        flag = "flag1"
    )

    val validImage = Image(
        id = 12,
        type = "",
        path = "",
        title = "",
        description = "",
        priority = 0,
        main = false,
        createdAt = "",
        updatedAt = ""
    )

    val validTourismVisa = TourismVisa(
        id = 1,
        user = validUser,
        firstName = "John",
        middleName = "A.",
        lastName = "Doe",
        gender = 1,
        birthdate = "1990-01-01",
        passportNumber = "X1234567",
        passportImages = listOf(validImage),
        attachments = listOf(validImage),
        destinationCountry = validCountry,
        purposeOfVisit = "Tourism",
        adultsCount = 2,
        childrenCount = 1,
        message = "Looking forward to the trip",
        nationality = validCountry,
        phone = validPhone,
        contactEmail = "test@gmail.com",
        statuses = emptyList(),
    )

    val validTourismVisaResponse = TourismVisaResponse(
        message = "Store Tourism Visa Successfully",
        tourismVisa = validTourismVisa,
    )

    val invalidTourismVisaRequestEmptyFirstName = validTourismVisaRequest.copy(firstName = "")
    val invalidTourismVisaRequestShortFirstName = validTourismVisaRequest.copy(firstName = "Jo")
    val invalidTourismVisaRequestEmptyMiddleName = validTourismVisaRequest.copy(middleName = "")
    val invalidTourismVisaRequestShortMiddleName =
        validTourismVisaRequest.copy(middleName = "a".repeat(20))
    val invalidTourismVisaRequestEmptyLastName = validTourismVisaRequest.copy(lastname = "")
    val invalidTourismVisaRequestShortLastName = validTourismVisaRequest.copy(lastname = "Do")
    val invalidTourismVisaRequestEmptyBirthDate = validTourismVisaRequest.copy(birthDate = "")
    val invalidTourismVisaRequestEmptyPassportNumber =
        validTourismVisaRequest.copy(passportNumber = "")
    val invalidTourismVisaRequestShortPassportNumber =
        validTourismVisaRequest.copy(passportNumber = "12345")
    val invalidTourismVisaRequestEmptyPhoneNumber = validTourismVisaRequest.copy(number = "")
    val invalidTourismVisaRequestShortPhoneNumber = validTourismVisaRequest.copy(number = "123")
    val invalidTourismVisaRequestEmptyCountryCode = validTourismVisaRequest.copy(countryCode = "")
    val invalidTourismVisaRequestShortCountryCode = validTourismVisaRequest.copy(countryCode = "1")
    val invalidTourismVisaRequestEmptyEmail = validTourismVisaRequest.copy(email = "")
    val invalidTourismVisaRequestInvalidEmail = validTourismVisaRequest.copy(email = "invalidemail")
    val invalidTourismVisaRequestEmptyPurposeOfVisit =
        validTourismVisaRequest.copy(purposeOfVisit = "")
    val invalidTourismVisaRequestShortPurposeOfVisit =
        validTourismVisaRequest.copy(purposeOfVisit = "tes")
    val invalidTourismVisaRequestInvalidAdultsCount = validTourismVisaRequest.copy(adultsCount = 12)
    val invalidTourismVisaRequestInvalidChildrenCount =
        validTourismVisaRequest.copy(childrenCount = 12)
    val invalidTourismVisaRequestLongMessage =
        validTourismVisaRequest.copy(message = "t".repeat(45000))
    val invalidTourismVisaRequestEmptyPassportImages =
        validTourismVisaRequest.copy(passportImages = emptyList())
    val invalidTourismVisaRequestEmptyAttachments =
        validTourismVisaRequest.copy(attachments = emptyList())

    val invalidTourismVisaRequestInvalidAttachments =
        validTourismVisaRequest.copy(attachments = listOf(File("test.png")))

    val invalidTourismVisaRequestInvalidPassportImages =
        validTourismVisaRequest.copy(passportImages = listOf(File("test.pdf")))


}