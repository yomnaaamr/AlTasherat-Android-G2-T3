package com.mahmoud.altasherat.features.onBoarding.onboarding.domain.useCase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.onBoarding.onboarding.data.repository.OnBoardingRepositoryImpl
import com.mahmoud.altasherat.features.onBoarding.onboarding.domain.repository.local.IOnBoardingLocalDS
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetOnBoardingStateUCTest {
    private lateinit var getOnBoardingStateUC: GetOnBoardingStateUC
    private lateinit var onBoardingRepository: OnBoardingRepositoryImpl

    private val onBoardingLocalDS: IOnBoardingLocalDS = mock()

    @Before
    fun setUp() {
        onBoardingRepository = OnBoardingRepositoryImpl(onBoardingLocalDS)
        getOnBoardingStateUC = GetOnBoardingStateUC(onBoardingRepository)
    }

    @Test
    fun `getOnBoardingState returns true when user completed onboarding`() = runTest {
        // Given
        whenever(onBoardingLocalDS.getOnBoardingState()).thenReturn(true)

        // When
        val flow = getOnBoardingStateUC()

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(Resource.Success(true))
            awaitComplete()
        }

        verify(onBoardingLocalDS, times(1)).getOnBoardingState()
    }

    @Test
    fun `getOnBoardingState returns false when user has not completed onboarding`() = runTest {
        // Given
        whenever(onBoardingLocalDS.getOnBoardingState()).thenReturn(false)

        // When
        val flow = getOnBoardingStateUC()

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(Resource.Success(false))
            awaitComplete()
        }

        verify(onBoardingLocalDS, times(1)).getOnBoardingState()
    }

    @Test
    fun `getOnBoardingState returns unknown error when exception is thrown`() = runTest {
        // Given
        val expectedException = AltasheratException(AltasheratError.UnknownError("Test Error"))

        whenever(onBoardingLocalDS.getOnBoardingState()).thenAnswer{throw expectedException}

        // When
        val flow = getOnBoardingStateUC()

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isEqualTo(AltasheratError.UnknownError("Test Error"))

            awaitComplete()
        }

        verify(onBoardingLocalDS, times(1)).getOnBoardingState()
    }


}