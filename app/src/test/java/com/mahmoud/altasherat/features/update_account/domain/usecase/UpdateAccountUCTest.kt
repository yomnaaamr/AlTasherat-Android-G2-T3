package com.mahmoud.altasherat.features.update_account.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.NetworkError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.update_account.data.MockUpdateAccountData
import com.mahmoud.altasherat.features.update_account.data.repository.UpdateAccRepository
import com.mahmoud.altasherat.features.update_account.domain.repository.IUpdateAccRepository
import com.mahmoud.altasherat.features.update_account.domain.repository.local.IUpdateAccLocalDS
import com.mahmoud.altasherat.features.update_account.domain.repository.remote.IUpdateAccRemoteDS
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class UpdateAccountUCTest {

    @Mock
    private lateinit var remoteDS: IUpdateAccRemoteDS

    @Mock
    private lateinit var localDS: IUpdateAccLocalDS

    private lateinit var repository: IUpdateAccRepository

    private lateinit var useCase: UpdateAccountUC

    private lateinit var validImage: File

    @Before
    fun setUp() {
        repository = UpdateAccRepository(remoteDS, localDS)
        useCase = UpdateAccountUC(repository)
        validImage = mock()
    }

    @Test
    fun `updateAccount returns success when request is valid`() = runTest {
        // Given
        val validRequest = MockUpdateAccountData.validUpdateAccRequest
        val expectedUpdateAcc = MockUpdateAccountData.validUpdateAcc
        val responseDto = MockUpdateAccountData.validUpdateAccDto

        whenever(remoteDS.updateRemoteUserInfo(validRequest)).thenReturn(responseDto)

        // When
        val flow = useCase(validRequest)

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(Resource.Success(expectedUpdateAcc))
            awaitComplete()
        }

        verify(remoteDS, times(1)).updateRemoteUserInfo(validRequest)
    }

    @Test
    fun `given empty first name when invoke then return invalid first name error`() = runTest {
        val invalidRequest = MockUpdateAccountData.invalidUpdateAccRequestEmptyFirstName

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_FIRSTNAME)
            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }

    @Test
    fun `given short first name when invoke then return invalid first name error`() = runTest {
        val invalidRequest = MockUpdateAccountData.invalidUpdateAccRequestShortFirstName

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_FIRSTNAME)
            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }

    @Test
    fun `given long middle name when invoke then return invalid middle name error`() = runTest {
        val invalidRequest = MockUpdateAccountData.invalidUpdateAccRequestShortMiddleName

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_MIDDLE_NAME)

            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }

    @Test
    fun `given empty last name when invoke then return invalid last name error`() = runTest {
        val invalidRequest = MockUpdateAccountData.invalidUpdateAccRequestEmptyLastName

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_LASTNAME)
            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }

    @Test
    fun `given short last name when invoke then return invalid last name error`() = runTest {
        val invalidRequest = MockUpdateAccountData.invalidUpdateAccRequestShortLastName

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_LASTNAME)
            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }

    @Test
    fun `given empty email when invoke then return invalid email error`() = runTest {
        val invalidRequest = MockUpdateAccountData.invalidUpdateAccRequestEmptyEmail

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_EMAIL)
            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }

    @Test
    fun `given invalid email when invoke then return invalid email error`() = runTest {
        val invalidRequest = MockUpdateAccountData.invalidUpdateAccRequestInvalidEmail

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_EMAIL)
            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }

    @Test
    fun `given invalid image extension when invoke then return invalid extension error`() = runTest {
        val image = mock<File>()
        whenever(image.name).thenReturn("test.pdf")
        whenever(image.extension).thenReturn("pdf")
        whenever(image.length()).thenReturn(100 * 1024)
        val invalidRequest = MockUpdateAccountData.validUpdateAccRequest.copy(image = image)

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_IMAGE_EXTENSION)
            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }

    @Test
    fun `given invalid image size when invoke then return invalid size error`() = runTest {
        val image = mock<File>()
        whenever(image.name).thenReturn("test.png")
        whenever(image.extension).thenReturn("png")
        whenever(image.length()).thenReturn(1024 * 1024)
        val invalidRequest = MockUpdateAccountData.validUpdateAccRequest.copy(image = image)

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_IMAGE_SIZE)
            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }
    @Test
    fun `given empty phone number when invoke then return invalid phone number error`() = runTest {
        val invalidRequest = MockUpdateAccountData.invalidUpdateAccRequestEmptyPhoneNumber

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_PHONE_NUMBER)
            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }

    @Test
    fun `given short phone number when invoke then return invalid phone number error`() = runTest {
        val invalidRequest = MockUpdateAccountData.invalidUpdateAccRequestShortPhoneNumber

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_PHONE_NUMBER)
            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }

    @Test
    fun `given empty country code when invoke then return invalid country code error`() = runTest {
        val invalidRequest = MockUpdateAccountData.invalidUpdateAccRequestEmptyCountryCode

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_COUNTRY_CODE)
            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }

    @Test
    fun `given short country code when invoke then return invalid country code error`() = runTest {
        val invalidRequest = MockUpdateAccountData.invalidUpdateAccRequestShortCountryCode

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_COUNTRY_CODE)
            awaitComplete()
        }

        verify(remoteDS, never()).updateRemoteUserInfo(any())
    }

    @Test
    fun `given repository throws exception when invoke then return unknown error`() = runTest {
        val validRequest = MockUpdateAccountData.validUpdateAccRequest
        val exception = RuntimeException("Test Exception")

        whenever(remoteDS.updateRemoteUserInfo(validRequest)).thenThrow(exception)

        useCase(validRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.UnknownError::class.java)
            awaitComplete()
        }

        verify(remoteDS, times(1)).updateRemoteUserInfo(validRequest)
    }

    @Test
    fun `given repository throws network error when invoke then return network error`() = runTest {
        val validRequest = MockUpdateAccountData.validUpdateAccRequest
        val exception = AltasheratException(NetworkError.NO_INTERNET)

        doAnswer { throw exception }.whenever(remoteDS).updateRemoteUserInfo(validRequest)

        useCase(validRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isEqualTo(NetworkError.NO_INTERNET)
            awaitComplete()
        }

        verify(remoteDS, times(1)).updateRemoteUserInfo(validRequest)
    }

    @Test
    fun `given repository throws specific AltasheratError when invoke then return that error`() =
        runTest {
            val validRequest = MockUpdateAccountData.validUpdateAccRequest
            val exception = AltasheratException(AltasheratError.UnknownError("Test Error"))

            doAnswer { throw exception }.whenever(remoteDS).updateRemoteUserInfo(validRequest)

            useCase(validRequest).test {
                assertThat(awaitItem()).isEqualTo(Resource.Loading)
                val errorResult = awaitItem() as Resource.Error
                assertThat(errorResult.error).isEqualTo(AltasheratError.UnknownError("Test Error"))
                awaitComplete()
            }

            verify(remoteDS, times(1)).updateRemoteUserInfo(validRequest)
        }





}