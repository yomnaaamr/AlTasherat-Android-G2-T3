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
class DeleteUserInfoUCTest {
    private lateinit var deleteUserInfoUC: DeleteUserInfoUC
    private lateinit var userRepository: UserInfoRepository

    private val userLocalDS: IUserInfoLocalDS = mock()

    @Before
    fun setUp() {
        userRepository = UserInfoRepository(userLocalDS)
        deleteUserInfoUC = DeleteUserInfoUC(userRepository)
    }

    @Test
    fun `deleteUserInfo returns success when user is deleted successfully`() = runTest {
        // Given
        whenever(userLocalDS.deleteUserInfo()).thenReturn(Unit)

        val flow = deleteUserInfoUC()

        // When & Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(Resource.Success(Unit))
            awaitComplete()
        }

        verify(userLocalDS, times(1)).deleteUserInfo()
    }

    @Test
    fun `deleteUserInfo returns unknown error when exception is thrown`() = runTest {
        // Given

        val expectedException = AltasheratException(AltasheratError.UnknownError("Test Error"))

        whenever(userLocalDS.deleteUserInfo()).thenAnswer { throw expectedException }

        // When
        val flow = deleteUserInfoUC()

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isEqualTo(AltasheratError.UnknownError("Test Error"))

            awaitComplete()
        }

        verify(userLocalDS, times(1)).deleteUserInfo()
    }

}