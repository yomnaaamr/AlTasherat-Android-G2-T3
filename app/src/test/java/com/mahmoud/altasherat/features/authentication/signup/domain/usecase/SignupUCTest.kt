package com.mahmoud.altasherat.features.authentication.signup.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.NetworkError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.authentication.signup.data.MockSignupData
import com.mahmoud.altasherat.features.authentication.signup.data.repository.SignupRepository
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.ISignupRepository
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.local.ISignupLocalDS
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.remote.ISignupRemoteDS
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doAnswer
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Unit tests for the [SignupUC] class.
 *
 * This test class verifies the behavior of the [SignupUC] under various scenarios,
 * including happy paths, validation failures, network errors, and unexpected exceptions.
 * It uses Mockito to mock the [ISignupRemoteDS] and [ISignupLocalDS] dependency to isolate the use case logic.
 * The tests leverage Turbine for testing Flows.
 *
 * The tests cover:
 * - Happy path scenarios (valid signup requests, successful responses).
 * - Validation failures (e.g., empty or invalid first name, email, password, phone).
 * - Network errors (e.g., [AltasheratError.NetworkError]).
 * - Handling of unexpected exceptions, emitting an [AltasheratError.UnknownError].
 */

@RunWith(MockitoJUnitRunner::class)
class SignupUCTest {


    @Mock
    private lateinit var remoteDS: ISignupRemoteDS

    @Mock
    private lateinit var localDS: ISignupLocalDS

    private lateinit var repository: ISignupRepository

    private lateinit var useCase: SignupUC

    @Before
    fun setUp() {
        repository = SignupRepository(remoteDS, localDS)
        useCase = SignupUC(repository)
    }

    @Test
    fun `given valid signup request when invoke then return success`() = runTest {
        val validRequest = MockSignupData.validSignUpRequest
        val expectedSignUp = MockSignupData.validSignUp
        val responseDto = MockSignupData.validSignUpDto

        whenever(remoteDS.signup(validRequest)).thenReturn(responseDto)

        useCase(validRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val successResult = awaitItem() as Resource.Success
            assertThat(successResult.data).isEqualTo(expectedSignUp)
            awaitComplete()
        }

        verify(remoteDS, times(1)).signup(validRequest)
    }

    @Test
    fun `given empty first name when invoke then return invalid first name error`() = runTest {
        val invalidRequest = MockSignupData.invalidSignUpRequestEmptyFirstName

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_FIRSTNAME)
            awaitComplete()
        }

        verify(remoteDS, never()).signup(any())
    }

    @Test
    fun `given short first name when invoke then return invalid first name error`() = runTest {
        val invalidRequest = MockSignupData.invalidSignUpRequestShortFirstName

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_FIRSTNAME)
            awaitComplete()
        }

        verify(remoteDS, never()).signup(any())
    }

    @Test
    fun `given empty last name when invoke then return invalid last name error`() = runTest {
        val invalidRequest = MockSignupData.invalidSignUpRequestEmptyLastName

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_LASTNAME)
            awaitComplete()
        }

        verify(remoteDS, never()).signup(any())
    }

    @Test
    fun `given short last name when invoke then return invalid last name error`() = runTest {
        val invalidRequest = MockSignupData.invalidSignUpRequestShortLastName

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_LASTNAME)
            awaitComplete()
        }

        verify(remoteDS, never()).signup(any())
    }

    @Test
    fun `given empty email when invoke then return invalid email error`() = runTest {
        val invalidRequest = MockSignupData.invalidSignUpRequestEmptyEmail

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_EMAIL)
            awaitComplete()
        }

        verify(remoteDS, never()).signup(any())
    }

    @Test
    fun `given invalid email when invoke then return invalid email error`() = runTest {
        val invalidRequest = MockSignupData.invalidSignUpRequestInvalidEmail

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_EMAIL)
            awaitComplete()
        }

        verify(remoteDS, never()).signup(any())
    }

    @Test
    fun `given empty password when invoke then return invalid password error`() = runTest {
        val invalidRequest = MockSignupData.invalidSignUpRequestEmptyPassword

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_PASSWORD)
            awaitComplete()
        }

        verify(remoteDS, never()).signup(any())
    }

    @Test
    fun `given short password when invoke then return invalid password error`() = runTest {
        val invalidRequest = MockSignupData.invalidSignUpRequestShortPassword

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_PASSWORD)
            awaitComplete()
        }

        verify(remoteDS, never()).signup(any())
    }

    @Test
    fun `given empty phone number when invoke then return invalid phone number error`() = runTest {
        val invalidRequest = MockSignupData.invalidSignUpRequestEmptyPhoneNumber

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_PHONE_NUMBER)
            awaitComplete()
        }

        verify(remoteDS, never()).signup(any())
    }

    @Test
    fun `given short phone number when invoke then return invalid phone number error`() = runTest {
        val invalidRequest = MockSignupData.invalidSignUpRequestShortPhoneNumber

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_PHONE_NUMBER)
            awaitComplete()
        }

        verify(remoteDS, never()).signup(any())
    }

    @Test
    fun `given empty country code when invoke then return invalid country code error`() = runTest {
        val invalidRequest = MockSignupData.invalidSignUpRequestEmptyCountryCode

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_COUNTRY_CODE)
            awaitComplete()
        }

        verify(remoteDS, never()).signup(any())
    }

    @Test
    fun `given short country code when invoke then return invalid country code error`() = runTest {
        val invalidRequest = MockSignupData.invalidSignUpRequestShortCountryCode

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_COUNTRY_CODE)
            awaitComplete()
        }

        verify(remoteDS, never()).signup(any())
    }

    @Test
    fun `given repository throws exception when invoke then return unknown error`() = runTest {
        val validRequest = MockSignupData.validSignUpRequest
        val exception = RuntimeException("Test Exception")

        whenever(remoteDS.signup(validRequest)).thenThrow(exception)

        useCase(validRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.UnknownError::class.java)
            awaitComplete()
        }

        verify(remoteDS, times(1)).signup(validRequest)
    }

    @Test
    fun `given repository throws network error when invoke then return network error`() = runTest {
        val validRequest = MockSignupData.validSignUpRequest
        val exception = AltasheratException(NetworkError.NO_INTERNET)

        doAnswer { throw exception }.whenever(remoteDS).signup(validRequest)

        useCase(validRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isEqualTo(NetworkError.NO_INTERNET)
            awaitComplete()
        }

        verify(remoteDS, times(1)).signup(validRequest)
    }

    @Test
    fun `given repository throws specific AltasheratError when invoke then return that error`() =
        runTest {
            val validRequest = MockSignupData.validSignUpRequest
            val exception = AltasheratException(AltasheratError.UnknownError("Test Error"))

            doAnswer { throw exception }.whenever(remoteDS).signup(validRequest)

            useCase(validRequest).test {
                assertThat(awaitItem()).isEqualTo(Resource.Loading)
                val errorResult = awaitItem() as Resource.Error
                assertThat(errorResult.error).isEqualTo(AltasheratError.UnknownError("Test Error"))
                awaitComplete()
            }

            verify(remoteDS, times(1)).signup(validRequest)
        }

}