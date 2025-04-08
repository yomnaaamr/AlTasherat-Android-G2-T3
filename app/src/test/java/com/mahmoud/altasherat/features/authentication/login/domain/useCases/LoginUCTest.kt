package com.mahmoud.altasherat.features.authentication.login.domain.useCases

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.NetworkError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.authentication.login.data.MockLoginData
import com.mahmoud.altasherat.features.authentication.login.data.repository.LoginRepository
import com.mahmoud.altasherat.features.authentication.login.domain.repository.ILoginRepository
import com.mahmoud.altasherat.features.authentication.login.domain.repository.local.ILoginLocalDS
import com.mahmoud.altasherat.features.authentication.login.domain.repository.remote.ILoginRemoteDS
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Unit tests for the [LoginUC] class.
 *
 * This test class verifies the behavior of the [LoginUC] under various scenarios,
 * including happy paths, validation failures, network errors, and unexpected exceptions.
 * It uses Mockito to mock the [ILoginRemoteDS] and [ILoginLocalDS] dependencies to isolate the use case logic.
 * The tests leverage Turbine for testing Flows.
 *
 * The tests cover:
 * - Happy path scenarios (valid login requests, successful responses).
 * - Validation failures (e.g., empty or invalid password, phone number, country code).
 * - Network errors (e.g., [AltasheratError.NetworkError]).
 * - Handling of unexpected exceptions, emitting an [AltasheratError.UnknownError].
 */
@RunWith(MockitoJUnitRunner::class)
class LoginUCTest {

    @Mock
    private lateinit var remoteDS: ILoginRemoteDS

    @Mock
    private lateinit var localDS: ILoginLocalDS

    private lateinit var repository: ILoginRepository

    private lateinit var useCase: LoginUC

    @Before
    fun setUp() {
        repository = LoginRepository(remoteDS, localDS)
        useCase = LoginUC(repository)
    }

    @Test
    fun `given valid login request when invoke then return success`() = runTest {
        val validRequest = MockLoginData.validLoginRequest
        val expectedLogin = MockLoginData.validLogin
        val responseDto = MockLoginData.validLoginDto

        whenever(remoteDS.login(validRequest)).thenReturn(responseDto)

        useCase(validRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val successResult = awaitItem() as Resource.Success
            assertThat(successResult.data).isEqualTo(expectedLogin)
            awaitComplete()
        }

        verify(remoteDS, times(1)).login(validRequest)
    }

    @Test
    fun `given empty password when invoke then return empty password error`() = runTest {
        val invalidRequest = MockLoginData.invalidLoginRequestEmptyPassword

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_PASSWORD)
            awaitComplete()
        }

        verify(remoteDS, never()).login(any())
    }

    @Test
    fun `given short password when invoke then return invalid password error`() = runTest {
        val invalidRequest = MockLoginData.invalidLoginRequestShortPassword

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_PASSWORD)
            awaitComplete()
        }

        verify(remoteDS, never()).login(any())
    }

    @Test
    fun `given empty phone number when invoke then return empty phone number error`() = runTest {
        val invalidRequest = MockLoginData.invalidLoginRequestEmptyPhoneNumber

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_PHONE_NUMBER)
            awaitComplete()
        }

        verify(remoteDS, never()).login(any())
    }

    @Test
    fun `given short phone number when invoke then return invalid phone number error`() = runTest {
        val invalidRequest = MockLoginData.invalidLoginRequestShortPhoneNumber

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_PHONE_NUMBER)
            awaitComplete()
        }

        verify(remoteDS, never()).login(any())
    }

    @Test
    fun `given empty country code when invoke then return empty country code error`() = runTest {
        val invalidRequest = MockLoginData.invalidLoginRequestEmptyCountryCode

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.EMPTY_COUNTRY_CODE)
            awaitComplete()
        }

        verify(remoteDS, never()).login(any())
    }

    @Test
    fun `given short country code when invoke then return invalid country code error`() = runTest {
        val invalidRequest = MockLoginData.invalidLoginRequestShortCountryCode

        useCase(invalidRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.ValidationErrors::class.java)
            val validationErrors = (errorResult.error as AltasheratError.ValidationErrors).errors
            assertThat(validationErrors).contains(ValidationError.INVALID_COUNTRY_CODE)
            awaitComplete()
        }

        verify(remoteDS, never()).login(any())
    }

    @Test
    fun `given repository throws exception when invoke then return unknown error`() = runTest {
        val validRequest = MockLoginData.validLoginRequest
        val exception = RuntimeException("Test Exception")

        whenever(remoteDS.login(validRequest)).thenThrow(exception)

        useCase(validRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.UnknownError::class.java)
            awaitComplete()
        }

        verify(remoteDS, times(1)).login(validRequest)
    }

    @Test
    fun `given repository throws network error when invoke then return network error`() = runTest {
        val validRequest = MockLoginData.validLoginRequest
        val exception = AltasheratException(NetworkError.NO_INTERNET)

        doAnswer { throw exception }.whenever(remoteDS).login(validRequest)

        useCase(validRequest).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isEqualTo(NetworkError.NO_INTERNET)
            awaitComplete()
        }

        verify(remoteDS, times(1)).login(validRequest)
    }

    @Test
    fun `given repository throws specific AltasheratError when invoke then return that error`() =
        runTest {
            val validRequest = MockLoginData.validLoginRequest
            val exception = AltasheratException(AltasheratError.UnknownError("Test Error"))

            doAnswer { throw exception }.whenever(remoteDS).login(validRequest)

            useCase(validRequest).test {
                assertThat(awaitItem()).isEqualTo(Resource.Loading)
                val errorResult = awaitItem() as Resource.Error
                assertThat(errorResult.error).isEqualTo(AltasheratError.UnknownError("Test Error"))
                awaitComplete()
            }

            verify(remoteDS, times(1)).login(validRequest)
        }
}