package com.mahmoud.altasherat.features.home.visa_requests.data

import com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.dto.CountryDto
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.ImageDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.UserDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.Image
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.Phone
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User
import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.AttachmentDto
import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.StatusDto
import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.TourismVisaRequestDto
import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.TourismVisaRequestsResponseDto
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.Attachment
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.Status
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequest
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequests

object MockTourismVisaRequestsData {

    private val validCountryDto = CountryDto(
        id = 1,
        name = "United States",
        currency = "USD",
        code = "US",
        phoneCode = "+1",
        flag = "/flags/us.png"
    )

    private val anotherValidCountryDto = CountryDto(
        id = 2,
        name = "Canada",
        currency = "CAD",
        code = "CA",
        phoneCode = "+1",
        flag = "/flags/ca.png"
    )

    private val validCountry = Country(
        id = 1,
        name = "United States",
        currency = "USD",
        code = "US",
        phoneCode = "+1",
        flag = "/flags/us.png"
    )

    private val anotherValidCountry = Country(
        id = 2,
        name = "Canada",
        currency = "CAD",
        code = "CA",
        phoneCode = "+1",
        flag = "/flags/ca.png"
    )

    private val validPhoneDto = PhoneDto(
        id = 1,
        countryCode = "+20",
        number = "1001234567",
        extension = null,
        type = "Mobile",
        holderName = "John Doe"
    )

    private val validImageDto = ImageDto(
        id = 1,
        type = "Passport",
        path = "/path/to/image.jpg",
        title = "Passport Front",
        description = "Front page of passport",
        priority = 1,
        main = true,
        createdAt = "2024-01-01T09:00:00Z",
        updatedAt = "2024-01-01T09:00:00Z"
    )

    private val validUserDto = UserDto(
        id = 1,
        userName = "johndoe",
        firstName = "John",
        middleName = "Doe",
        lastName = "Smith",
        email = "john.doe@example.com",
        phone = validPhoneDto,
        image = validImageDto,
        birthDate = "1990-05-15",
        emailVerified = true,
        phoneVerified = true,
        isBlocked = 0
    )

    private val validPhone = Phone(
        id = 1,
        countryCode = "+20",
        number = "1001234567",
        extension = "",
        type = "Mobile",
        holderName = "John Doe"
    )

    private val validImage = Image(
        id = 1,
        type = "Passport",
        path = "/path/to/image.jpg",
        title = "Passport Front",
        description = "Front page of passport",
        priority = 1,
        main = true,
        createdAt = "2024-01-01T09:00:00Z",
        updatedAt = "2024-01-01T09:00:00Z"
    )

    private val validUser = User(
        id = 1,
        username = "johndoe",
        firstname = "John",
        middleName = "Doe",
        lastname = "Smith",
        email = "john.doe@example.com",
        phone = validPhone,
        image = validImage,
        birthDate = "1990-05-15",
        emailVerified = true,
        phoneVerified = true,
        isBlocked = 0
    )

    private val validAttachmentDto = AttachmentDto(
        id = 1,
        type = "Passport Copy",
        path = "/path/to/passport.pdf",
        title = "Passport",
        description = "Copy of passport",
        priority = 1,
        main = true,
        createdAt = "2024-01-01T10:00:00Z",
        updatedAt = "2024-01-01T10:00:00Z"
    )

    private val validAttachment = Attachment(
        id = 1,
        type = "Passport Copy",
        path = "/path/to/passport.pdf",
        title = "Passport",
        description = "Copy of passport",
        priority = 1,
        main = true,
        createdAt = "2024-01-01T10:00:00Z",
        updatedAt = "2024-01-01T10:00:00Z"
    )

    private val validStatusDto = StatusDto(
        id = 1,
        name = "Pending",
        color = "#FFFF00",
        activatedAt = "2024-01-02T12:00:00Z",
        main = true,
        active = true
    )

    private val validStatus = Status(
        id = 1,
        name = "Pending",
        color = "#FFFF00",
        activatedAt = "2024-01-02T12:00:00Z",
        main = true,
        active = true
    )

    private val validTourismVisaRequestDto = TourismVisaRequestDto(
        id = 101,
        user = validUserDto,
        firstName = "John",
        middleName = "Doe",
        lastName = "Smith",
        gender = 1,
        birthDate = "1990-05-15",
        nationality = validCountryDto,
        passportNumber = "AB1234567",
        passportImages = listOf(validImageDto),
        attachments = listOf(validAttachmentDto),
        phone = validPhoneDto,
        contactEmail = "john.doe@example.com",
        destinationCountry = anotherValidCountryDto,
        purposeOfVisit = "Tourism",
        adultsCount = 2,
        childrenCount = 1,
        message = "Requesting a tourist visa.",
        statuses = listOf(validStatusDto)
    )

    private val validTourismVisaRequest = TourismVisaRequest(
        id = 101,
        user = validUser,
        firstName = "John",
        middleName = "Doe",
        lastName = "Smith",
        gender = 1,
        birthDate = "1990-05-15",
        nationality = validCountry,
        passportNumber = "AB1234567",
        passportImages = listOf(validImage),
        attachments = listOf(validAttachment),
        phone = validPhone,
        contactEmail = "john.doe@example.com",
        destinationCountry = anotherValidCountry,
        purposeOfVisit = "Tourism",
        adultsCount = 2,
        childrenCount = 1,
        message = "Requesting a tourist visa.",
        statuses = listOf(validStatus)
    )

    val validTourismVisaRequestsDto = TourismVisaRequestsResponseDto(
        data = listOf(validTourismVisaRequestDto)
    )

    val validTourismVisaRequests = TourismVisaRequests(
        visaRequests = listOf(validTourismVisaRequest)
    )

    val emptyTourismVisaRequestsDto = TourismVisaRequestsResponseDto(
        data = emptyList()
    )

    val emptyTourismVisaRequests = TourismVisaRequests(
        visaRequests = emptyList()
    )
}