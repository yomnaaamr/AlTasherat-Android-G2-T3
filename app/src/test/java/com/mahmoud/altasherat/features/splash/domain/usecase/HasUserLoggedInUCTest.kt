package com.mahmoud.altasherat.features.splash.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.NetworkError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.local.ISignupLocalDS
import com.mahmoud.altasherat.features.authentication.signup.domain.repository.remote.ISignupRemoteDS
import com.mahmoud.altasherat.features.splash.data.repository.SplashRepository
import com.mahmoud.altasherat.features.splash.domain.repository.ISplashRepository
import com.mahmoud.altasherat.features.splash.domain.repository.local.ISplashLocalDS
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Unit tests for the [HasUserLoggedInUC] class.
 *
 * This test class verifies the behavior of the [HasUserLoggedInUC] under various scenarios,
 * including happy paths and error cases. It uses Mockito to mock the [ISplashLocalDS]
 * dependency to isolate the use case logic. The tests leverage Turbine for testing Flows.
 *
 * The tests cover:
 * - Happy path scenarios (user is logged in, user is not logged in).
 * - Error scenarios (unexpected exceptions, specific [AltasheratError]).
 */
@RunWith(MockitoJUnitRunner::class)
class HasUserLoggedInUCTest {


    @Mock
    private lateinit var localDS: ISplashLocalDS

    private lateinit var repository: ISplashRepository

    private lateinit var useCase: HasUserLoggedInUC

    @Before
    fun setUp() {
        repository = SplashRepository(localDS)
        useCase = HasUserLoggedInUC(repository)
    }

    @Test
    fun `given user is logged in when invoke then return true`() = runTest {
        whenever(localDS.hasUserLoggedIn()).thenReturn(true)

        useCase().test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val successResult = awaitItem() as Resource.Success
            assertThat(successResult.data).isEqualTo(true)
            awaitComplete()
        }

        verify(localDS, times(1)).hasUserLoggedIn()
    }

    @Test
    fun `given user is not logged in when invoke then return false`() = runTest {
        whenever(localDS.hasUserLoggedIn()).thenReturn(false)

        useCase().test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val successResult = awaitItem() as Resource.Success
            assertThat(successResult.data).isEqualTo(false)
            awaitComplete()
        }

        verify(localDS, times(1)).hasUserLoggedIn()
    }



    @Test
    fun `given repository throws specific AltasheratError when invoke then return that error`() =
        runTest {
            val exception = AltasheratException(AltasheratError.UnknownError("Test Error"))
            doAnswer { throw exception }.whenever(localDS).hasUserLoggedIn()

            useCase().test {
                assertThat(awaitItem()).isEqualTo(Resource.Loading)
                val errorResult = awaitItem() as Resource.Error
                assertThat(errorResult.error).isEqualTo(AltasheratError.UnknownError("Test Error"))
                awaitComplete()
            }

            verify(localDS, times(1)).hasUserLoggedIn()
        }
}