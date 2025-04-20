package com.mahmoud.altasherat.features.al_tashirat_services.user.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.repository.UserInfoRepository
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.repository.local.IUserInfoLocalDS
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
class GetUserAccessTokenTest {
    private lateinit var getUserTokenUC: GetUserAccessToken
    private lateinit var userRepository: UserInfoRepository

    private val userLocalDS: IUserInfoLocalDS = mock()

    @Before
    fun setUp() {
        userRepository = UserInfoRepository(userLocalDS)
        getUserTokenUC = GetUserAccessToken(userRepository)
    }

    @Test
    fun `getUserAccessToken returns success when token is retrieved successfully`() = runTest {
        // Given
        val expectedToken = "user_token_123"
        whenever(userLocalDS.getUserAccessToken()).thenReturn(expectedToken)

        val flow = getUserTokenUC()

        // When & Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(Resource.Success(expectedToken))
            awaitComplete()
        }

        verify(userLocalDS, times(1)).getUserAccessToken()
    }

    @Test
    fun `getUserAccessToken returns unknown error when exception is thrown`() = runTest {
        // Given
        val exception = RuntimeException("Database failure")
        whenever(userRepository.getUserAccessToken()).thenThrow(exception)

        val flow = getUserTokenUC()

        // When & Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)

            val errorItem = awaitItem()
            assertThat(errorItem).isInstanceOf(Resource.Error::class.java)

            val error = (errorItem as Resource.Error).error
            assertThat(error).isInstanceOf(AltasheratError.UnknownError::class.java)
            assertThat((error as AltasheratError.UnknownError).unknownErrorMessage).contains("Unknown error")

            awaitComplete()
        }


    }
}