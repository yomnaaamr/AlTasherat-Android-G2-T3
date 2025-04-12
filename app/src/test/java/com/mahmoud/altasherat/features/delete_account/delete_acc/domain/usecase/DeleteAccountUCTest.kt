package com.mahmoud.altasherat.features.delete_account.delete_acc.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.NetworkError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.delete_account.delete_acc.data.mappers.DeleteAccMapper
import com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.dto.DeleteAccDto
import com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.request.DeleteAccRequest
import com.mahmoud.altasherat.features.delete_account.delete_acc.data.repository.DeleteAccountRepository
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository.remote.IDeleteAccountRemoteDS
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
class DeleteAccountUCTest {

    private lateinit var deleteAccountUC: DeleteAccountUC
    private lateinit var deleteAccRepository: DeleteAccountRepository

    private val deleteAccRemoteDS: IDeleteAccountRemoteDS = mock()

    @Before
    fun setUp() {
        deleteAccRepository = DeleteAccountRepository(deleteAccRemoteDS)
        deleteAccountUC = DeleteAccountUC(deleteAccRepository)
    }

    @Test
    fun `deleteAccount returns validation error when password is blank`() = runTest {
        // Given
        val request = DeleteAccRequest(password = "")

        // When
        val flow = deleteAccountUC(request)

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)

            val errorItem = awaitItem()
            assertThat(errorItem).isEqualTo(
                Resource.Error(
                    AltasheratError.ValidationErrors(
                        listOf(ValidationError.EMPTY_PASSWORD)
                    )
                )
            )
            awaitComplete()
        }
    }

    @Test
    fun `deleteAccount returns success when password is valid`() = runTest {
        // Given
        val request = DeleteAccRequest(password = "validPassword123")
        val response = DeleteAccDto(message = "Account deleted successfully")

        whenever(deleteAccRemoteDS.deleteAccount(request)).thenReturn(response)

        // When
        val flow = deleteAccountUC(request)

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(Resource.Success(DeleteAccMapper.dtoToDomain(response)))
            awaitComplete()
        }

        verify(deleteAccRemoteDS, times(1)).deleteAccount(request)
    }

    @Test
    fun `changePassword returns exception when exception occurs`() = runTest {
        // Given
        val request = DeleteAccRequest(password = "validPassword123")
        val expectedException = AltasheratException(NetworkError.NO_INTERNET)

        whenever(deleteAccRepository.deleteAccount(request)).thenAnswer {
            expectedException
        }

        // When
        val flow = deleteAccountUC(request)

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


}