package com.mahmoud.altasherat.features.menu_options.change_password.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.NetworkError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.menu_options.change_password.data.mappers.ChangePasswordMapper
import com.mahmoud.altasherat.features.menu_options.change_password.data.models.dto.ChangePasswordDto
import com.mahmoud.altasherat.features.menu_options.change_password.data.models.request.ChangePassRequest
import com.mahmoud.altasherat.features.menu_options.change_password.data.repository.ChangePassRepository
import com.mahmoud.altasherat.features.menu_options.change_password.domain.repository.remote.IChangePassRemoteDS
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
class ChangePasswordUCTest {

    private lateinit var changePassUC: ChangePasswordUC
    private lateinit var changePassRepository: ChangePassRepository


    private val changePassRemoteDS: IChangePassRemoteDS = mock()

    @Before
    fun setup() {
        changePassRepository = ChangePassRepository(changePassRemoteDS)
        changePassUC = ChangePasswordUC(changePassRepository)
    }

    @Test
    fun `changePassword returns validation error when all fields are blank`() = runTest {
        // Given
        val request = ChangePassRequest("", "", "")

        val flow = changePassUC(request)
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)

            val errorItem = awaitItem()
            assertThat(errorItem).isEqualTo(
                Resource.Error(
                    AltasheratError.ValidationErrors(
                        listOf(
                            ValidationError.EMPTY_OLD_PASSWORD,
                            ValidationError.EMPTY_NEW_PASSWORD,
                            ValidationError.EMPTY_PASSWORD_CONFIRMATION
                        )
                    )
                )
            )
            awaitComplete()
        }
    }

    @Test
    fun `changePassword returns validation error when oldPassword is blank`() = runTest {
        // Given
        val request = ChangePassRequest(
            oldPassword = "",
            newPassword = "newPassword123",
            newPasswordConfirmation = "newPassword123"
        )

        // When
        val flow = changePassUC(request)

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)

            val errorItem = awaitItem()
            assertThat(errorItem).isEqualTo(
                Resource.Error(
                    AltasheratError.ValidationErrors(listOf(ValidationError.EMPTY_OLD_PASSWORD))
                )
            )
            awaitComplete()
        }
    }

    @Test
    fun `changePassword returns validation error when newPassword is blank`() = runTest {
        // Given
        val request = ChangePassRequest(
            oldPassword = "oldPassword", newPassword = "", newPasswordConfirmation = ""
        )

        // When
        val flow = changePassUC(request)

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)

            val errorItem = awaitItem()
            assertThat(errorItem).isEqualTo(
                Resource.Error(
                    AltasheratError.ValidationErrors(
                        listOf(
                            ValidationError.EMPTY_NEW_PASSWORD,
                            ValidationError.EMPTY_PASSWORD_CONFIRMATION
                        )
                    )
                )
            )
            awaitComplete()
        }
    }

    @Test
    fun `changePassword returns validation error when newPassword is too short`() = runTest {
        // Given
        val request = ChangePassRequest(
            oldPassword = "oldPassword", newPassword = "short", newPasswordConfirmation = "short"
        )

        // When
        val flow = changePassUC(request)

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)

            val errorItem = awaitItem()
            assertThat(errorItem).isEqualTo(
                Resource.Error(
                    AltasheratError.ValidationErrors(
                        listOf(
                            ValidationError.INVALID_NEW_PASSWORD,
                            ValidationError.INVALID_PASSWORD_CONFIRMATION
                        )
                    )
                )
            )
            awaitComplete()
        }
    }

    @Test
    fun `changePassword returns validation error when passwordConfirmation is blank`() = runTest {
        // Given
        val request = ChangePassRequest(
            oldPassword = "oldPassword", newPassword = "newPassword", newPasswordConfirmation = ""
        )

        // When
        val flow = changePassUC(request)

        // Then
        flow.test {

            assertThat(awaitItem()).isEqualTo(Resource.Loading)

            val errorItem = awaitItem()
            assertThat(errorItem).isEqualTo(
                Resource.Error(
                    AltasheratError.ValidationErrors(listOf(ValidationError.EMPTY_PASSWORD_CONFIRMATION))
                )
            )
            awaitComplete()
        }
    }

    @Test
    fun `changePassword returns validation error when passwordConfirmation doesn't match`() =
        runTest {
            // Given
            val request = ChangePassRequest(
                oldPassword = "oldPassword",
                newPassword = "newPassword",
                newPasswordConfirmation = "passwordConfirmation"
            )

            // When
            val flow = changePassUC(request)

            // Then
            flow.test {

                assertThat(awaitItem()).isEqualTo(Resource.Loading)
                val errorItem = awaitItem()
                assertThat(errorItem).isEqualTo(
                    Resource.Error(
                        AltasheratError.ValidationErrors(listOf(ValidationError.INVALID_PASSWORD_CONFIRMATION))
                    )
                )
                awaitComplete()
            }
        }

    @Test
    fun `changePassword returns exception when exception occurs`() = runTest {
        // Given
        val request = ChangePassRequest(
            oldPassword = "oldPassword",
            newPassword = "newPassword",
            newPasswordConfirmation = "newPassword"
        )
        val expectedException = AltasheratException(NetworkError.NO_INTERNET)

        whenever(changePassRepository.changePassword(request)).thenAnswer {
            expectedException
        }

        // When
        val flow = changePassUC(request)

        // Then
        flow.test {

            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorItem = awaitItem()
            assertThat(errorItem).isInstanceOf(Resource.Error::class.java)
            assertThat((errorItem as Resource.Error).error).isInstanceOf(
                AltasheratError.UnknownError::class.java
            )
            awaitComplete()
        }
    }

    @Test
    fun `changePassword returns success message when changePassword success`() = runTest {
        // Given
        val request = ChangePassRequest(
            oldPassword = "oldPassword",
            newPassword = "newPassword",
            newPasswordConfirmation = "newPassword"
        )
//        val token =
//            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiZGMyNzFjYmE3N2I2NzRmOGYwZTJkYTM1ZjlhNzQ3MzdkMTNjYjBlZWE0NzQ0ODdhOWZlYzkyOWZkMGQwZTE5MjZiYzQxZDQ1NzZiYmU5NjUiLCJpYXQiOjE3NDM0MjcxNDYuMjk0ODMyLCJuYmYiOjE3NDM0MjcxNDYuMjk0ODM1LCJleHAiOjE3NzQ5NjMxNDYuMjg4MjYyLCJzdWIiOiI0OTYiLCJzY29wZXMiOltdfQ.yf5q5AYa4eNcy6t-FivV7VikYk7HFmwoEDecGY1F1wyrIisDJ_fwrkgXPYKnqmZ90HqCM_iVq0wu9cfb5q3ahIE916mmfdwSx2xaxMSnqyLyF2xgxH8c3RiCNKxhMPJVpsE9Ue_jkyBDmO6qmpQBtXFmp99-jPR2LjJ8Cvx8VDCvxVWJ5rFbXtzPxJfOzvflLSep1u_rGkpirY58wL7dLEsfHhaBKyWEe2h2eYuR8ig_Nn3na5PlRpCBMRCloU4ExtkaeSOgTC68861tzcOsQNwi8X3jy5cxAcXrcsPauBHc1Z9rO5claWW-ZWcoXPNw533vrnXFK8kbiOfJgpNCTFpDVbL4OmX4f4CSeHtHsSz4VTz-E62CJyi893HJwVvLKdZYgo5wMF2YsI-s8RrdGC51q7SqKXalH3VRLcy1ILCEwtUZfDPDFBK6rPDU4J2m2AYu0cXrnklok4C_lHehkXgKV9v-B7MJIYyepTaTyM7ZvRUT_-UfqLnWlPVRUoaC3XY2c4nlROd1sJVBFpkQ--XywWklvZnSW8eJPvKHvV6A5-aUhfUK5FaFQQGAY2XTVDXw5ONkypSCY5Ck2v1Ga72yi_UFBEO-dI69HkbN2pFgwfuxIv2QJ0eG-1zB6Apxbet_8gjuYc7nwAM--DjDfVrXFoB3RXUXLQWXrtej57k"

        val response = ChangePasswordDto(message = "")

        whenever(changePassRemoteDS.changePassword(request)).thenReturn(response)

        // When
        val flow = changePassUC(request)

        // Then
        flow.test {

            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val responseItem = awaitItem()
            assertThat(responseItem).isEqualTo(
                Resource.Success(
                    ChangePasswordMapper.dtoToDomain(response)
                )
            )
            awaitComplete()

        }
        verify(changePassRemoteDS, times(1)).changePassword(request)
    }
}