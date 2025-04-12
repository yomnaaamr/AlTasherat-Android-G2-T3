package com.mahmoud.altasherat.features.al_tashirat_services.user.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
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
class DeleteUserAccessTokenUCTest {
    private lateinit var deleteUserTokenUC: DeleteUserAccessTokenUC
    private lateinit var userRepository: UserInfoRepository

    private val userLocalDS: IUserInfoLocalDS = mock()

    @Before
    fun setUp() {
        userRepository = UserInfoRepository(userLocalDS)
        deleteUserTokenUC = DeleteUserAccessTokenUC(userRepository)
    }

    @Test
    fun `deleteUserAccessToken returns success when token is deleted successfully`() = runTest {
        // Given
        whenever(userLocalDS.deleteUserAccessToken()).thenReturn(Unit)

        val flow = deleteUserTokenUC()

        // When & Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(Resource.Success(Unit))
            awaitComplete()
        }

        verify(userLocalDS, times(1)).deleteUserAccessToken()
    }

    @Test
    fun `deleteUserAccessToken returns unknown error when exception is thrown`() = runTest {
        // Given

        val expectedException = AltasheratException(AltasheratError.UnknownError("Test Error"))

        whenever(userLocalDS.deleteUserAccessToken()).thenAnswer { throw expectedException }

        // When
        val flow = deleteUserTokenUC()

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isEqualTo(AltasheratError.UnknownError("Test Error"))

            awaitComplete()
        }

        verify(userLocalDS, times(1)).deleteUserAccessToken()
    }

}