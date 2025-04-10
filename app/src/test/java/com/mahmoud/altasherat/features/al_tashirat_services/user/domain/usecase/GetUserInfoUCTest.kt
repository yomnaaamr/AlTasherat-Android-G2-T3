package com.mahmoud.altasherat.features.al_tashirat_services.user.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.MockUserData
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.UserMapper
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
class GetUserInfoUCTest {
    private lateinit var getUserInfoUC: GetUserInfoUC
    private lateinit var userRepository: UserInfoRepository

    private val userLocalDS: IUserInfoLocalDS = mock()

    @Before
    fun setUp() {
        userRepository = UserInfoRepository(userLocalDS)
        getUserInfoUC = GetUserInfoUC(userRepository)
    }

    @Test
    fun `getUserInfo returns success when user data is retrieved successfully`() = runTest {
        // Given
        whenever(userLocalDS.getUserInfo()).thenReturn(MockUserData.userResponse)

        val flow = getUserInfoUC()

        // When & Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(
                Resource.Success(
                    UserMapper.entityToDomain(
                        MockUserData.userResponse
                    )
                )
            )
            awaitComplete()
        }

        verify(userLocalDS, times(1)).getUserInfo()
    }

    @Test
    fun `getUserInfo returns unknown error when exception is thrown`() = runTest {
        // Given
        val exception = RuntimeException("Something went wrong")
        whenever(userLocalDS.getUserInfo()).thenThrow(exception)

        val flow = getUserInfoUC()

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

        verify(userLocalDS, times(1)).getUserInfo()
    }
}