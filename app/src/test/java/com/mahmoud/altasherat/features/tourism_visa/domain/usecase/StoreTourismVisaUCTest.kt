package com.mahmoud.altasherat.features.tourism_visa.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.features.tourism_visa.data.MockTourismVisaData
import com.mahmoud.altasherat.features.tourism_visa.data.models.request.StoreTourismVisaRequest
import com.mahmoud.altasherat.features.tourism_visa.data.repository.TourismVisaRepository
import com.mahmoud.altasherat.features.tourism_visa.domain.repository.ITourismVisaRepository
import com.mahmoud.altasherat.features.tourism_visa.domain.repository.remote.ITourismVisaRemoteDS
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class StoreTourismVisaUCTest {
    @Mock
    private lateinit var remoteDS: ITourismVisaRemoteDS

    private lateinit var repository: ITourismVisaRepository

    private lateinit var useCase: StoreTourismVisaUC

    private lateinit var validImageList: List<File>
    private lateinit var validAttachmentList: List<File>

    @Before
    fun setUp() {
        repository = TourismVisaRepository(remoteDS)
        useCase = StoreTourismVisaUC(repository)
        validImageList = mock()
    }

    @Test
    fun `given valid request when invoke then return success`() = runTest {
        val validRequest = MockTourismVisaData.validTourismVisaRequest
        val expectedResponse = MockTourismVisaData.validTourismVisaResponse
        val responseDto = MockTourismVisaData.validTourismVisaResponseDto

        whenever(remoteDS.storeTourismVisa(validRequest)).thenReturn(responseDto)

        useCase(validRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(Resource.Success(expectedResponse))
            awaitComplete()
        }

        verify(remoteDS, times(1)).storeTourismVisa(validRequest)
    }

    @Test
    fun `empty first name returns EMPTY_FIRSTNAME`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestEmptyFirstName,
        expectedError = ValidationError.EMPTY_FIRSTNAME
    )

    @Test
    fun `invalid first name length returns INVALID_FIRSTNAME`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestShortFirstName,
        expectedError = ValidationError.INVALID_FIRSTNAME
    )

    @Test
    fun `empty middle name returns EMPTY_MIDDLE_NAME`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestEmptyMiddleName,
        expectedError = ValidationError.EMPTY_MIDDLE_NAME
    )

    @Test
    fun `invalid middle name length returns INVALID_MIDDLE_NAME`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestShortMiddleName,
        expectedError = ValidationError.INVALID_MIDDLE_NAME
    )

    @Test
    fun `empty last name returns EMPTY_LASTNAME`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestEmptyLastName,
        expectedError = ValidationError.EMPTY_LASTNAME
    )

    @Test
    fun `invalid last name length returns INVALID_LASTNAME`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestShortLastName,
        expectedError = ValidationError.INVALID_LASTNAME
    )

    @Test
    fun `empty birth date returns EMPTY_BIRTHDATE`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestEmptyBirthDate,
        expectedError = ValidationError.EMPTY_BIRTHDATE
    )

    @Test
    fun `empty passport number returns EMPTY_PASSPORT_NUMBER`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestEmptyPassportNumber,
        expectedError = ValidationError.EMPTY_PASSPORT_NUMBER
    )

    @Test
    fun `invalid passport number returns INVALID_PASSPORT_NUMBER`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestShortPassportNumber,
        expectedError = ValidationError.INVALID_PASSPORT_NUMBER
    )

    @Test
    fun `empty passport images returns EMPTY_PASSPORT_IMAGES`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestEmptyPassportImages,
        expectedError = ValidationError.EMPTY_PASSPORT_IMAGES
    )

    @Test
    fun `invalid passport images return INVALID_PASSPORT_IMAGES`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestInvalidPassportImages,
        expectedError = ValidationError.INVALID_PASSPORT_IMAGES
    )

    @Test
    fun `empty attachments return EMPTY_ATTACHMENTS`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestEmptyAttachments,
        expectedError = ValidationError.EMPTY_ATTACHMENTS
    )

    @Test
    fun `invalid attachments return INVALID_ATTACHMENTS`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestInvalidAttachments,
        expectedError = ValidationError.INVALID_ATTACHMENTS
    )

    @Test
    fun `empty email returns EMPTY_EMAIL`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestEmptyEmail,
        expectedError = ValidationError.EMPTY_EMAIL
    )

    @Test
    fun `malformed email returns INVALID_EMAIL`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestInvalidEmail,
        expectedError = ValidationError.INVALID_EMAIL
    )

    @Test
    fun `empty phone number returns EMPTY_PHONE_NUMBER`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestEmptyPhoneNumber,
        expectedError = ValidationError.EMPTY_PHONE_NUMBER
    )

    @Test
    fun `invalid phone number returns INVALID_PHONE_NUMBER`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestShortPhoneNumber,
        expectedError = ValidationError.INVALID_PHONE_NUMBER
    )

    @Test
    fun `empty country code returns EMPTY_COUNTRY_CODE`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestEmptyCountryCode,
        expectedError = ValidationError.EMPTY_COUNTRY_CODE
    )

    @Test
    fun `invalid country code returns INVALID_COUNTRY_CODE`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestShortCountryCode,
        expectedError = ValidationError.INVALID_COUNTRY_CODE
    )

    @Test
    fun `empty purpose returns EMPTY_PURPOSE`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestEmptyPurposeOfVisit,
        expectedError = ValidationError.EMPTY_PURPOSE
    )

    @Test
    fun `short purpose returns INVALID_PURPOSE`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestShortPurposeOfVisit,
        expectedError = ValidationError.INVALID_PURPOSE
    )

    @Test
    fun `invalid adults count returns INVALID_ADULTS_COUNT`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestInvalidAdultsCount,
        expectedError = ValidationError.INVALID_ADULTS_COUNT
    )

    @Test
    fun `invalid children count returns INVALID_CHILDREN_COUNT`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestInvalidChildrenCount,
        expectedError = ValidationError.INVALID_CHILDREN_COUNT
    )

    @Test
    fun `too long message returns INVALID_VISA_MESSAGE`() = runValidationErrorTest(
        request = MockTourismVisaData.invalidTourismVisaRequestLongMessage,
        expectedError = ValidationError.INVALID_VISA_MESSAGE
    )

    private fun runValidationErrorTest(
        request: StoreTourismVisaRequest,
        expectedError: ValidationError
    ) = runTest {
        useCase(request).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(expectedError)
            awaitComplete()
        }

        verify(remoteDS, never()).storeTourismVisa(any())
    }

}