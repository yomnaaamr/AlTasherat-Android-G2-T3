package com.mahmoud.altasherat.features.home.visa_requests.domain.useCases

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.NetworkError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.home.visa_requests.data.MockTourismVisaRequestsData
import com.mahmoud.altasherat.features.home.visa_requests.data.repository.TourismVisaRequestsRepository
import com.mahmoud.altasherat.features.home.visa_requests.domain.repository.ITourismVisaRequestsRepository
import com.mahmoud.altasherat.features.home.visa_requests.domain.repository.remote.ITourismVisaRequestsRemoteDS
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
 * Unit tests for the [GetTourismVisaRequestsUC] class.
 *
 * This test class verifies the behavior of the [GetTourismVisaRequestsUC] under various scenarios,
 * including happy paths and error cases. It uses Mockito to mock the [ITourismVisaRequestsRemoteDS]
 * dependency to simulate different API responses. The tests leverage Turbine for testing Flows.
 *
 * The tests cover:
 * - Happy path scenarios (successful retrieval of tourism visa requests).
 * - Empty data scenarios (API returns no data).
 * - Network error scenarios (API throws [java.io.IOException] wrapped in [AltasheratException]).
 * - Unauthorized error scenarios (API throws [AltasheratException] with [NetworkError.UNAUTHORIZED]).
 * - Unknown exception scenarios (API throws unexpected [RuntimeException]).
 * - Null response scenarios (API returns `null`).
 */

@RunWith(MockitoJUnitRunner::class)
class GetTourismVisaRequestsUCTest {

    @Mock
    private lateinit var remoteDS: ITourismVisaRequestsRemoteDS

    private lateinit var repository: ITourismVisaRequestsRepository

    private lateinit var useCase: GetTourismVisaRequestsUC

    @Before
    fun setUp() {
        repository = TourismVisaRequestsRepository(remoteDS)
        useCase = GetTourismVisaRequestsUC(repository)
    }

    @Test
    fun `given valid language code and successful API response when invoke then return success with domain data`() =
        runTest {
            val languageCode = "en"
            val mockApiResponse = MockTourismVisaRequestsData.validTourismVisaRequestsDto
            val expectedDomain = MockTourismVisaRequestsData.validTourismVisaRequests

            whenever(remoteDS.getTourismVisaRequests(languageCode)).thenReturn(mockApiResponse)

            useCase(languageCode).test {
                assertThat(awaitItem()).isEqualTo(Resource.Loading)
                val successResult = awaitItem() as Resource.Success
                assertThat(successResult.data).isEqualTo(expectedDomain)
                awaitComplete()
            }

            verify(remoteDS, times(1)).getTourismVisaRequests(languageCode)
        }

    @Test
    fun `given valid language code and API returns empty data when invoke then return success with empty list`() =
        runTest {
            val languageCode = "en"
            val mockApiResponse = MockTourismVisaRequestsData.emptyTourismVisaRequestsDto
            val expectedDomain = MockTourismVisaRequestsData.emptyTourismVisaRequests

            whenever(remoteDS.getTourismVisaRequests(languageCode)).thenReturn(mockApiResponse)

            useCase(languageCode).test {
                assertThat(awaitItem()).isEqualTo(Resource.Loading)
                val successResult = awaitItem() as Resource.Success
                assertThat(successResult.data).isEqualTo(expectedDomain)
                awaitComplete()
            }

            verify(remoteDS, times(1)).getTourismVisaRequests(languageCode)
        }

    @Test
    fun `given valid language code and API throws IOException when invoke then return network error`() =
        runTest {
            val languageCode = "en"
            val exception = AltasheratException(NetworkError.NO_INTERNET)

            whenever(remoteDS.getTourismVisaRequests(languageCode)).doAnswer { throw exception }

            useCase(languageCode).test {
                assertThat(awaitItem()).isEqualTo(Resource.Loading)
                val errorResult = awaitItem() as Resource.Error
                assertThat(errorResult.error).isInstanceOf(NetworkError::class.java)
                awaitComplete()
            }

            verify(
                remoteDS,
                times(1)
            ).getTourismVisaRequests(languageCode)
        }


    @Test
    fun `given valid language code and API throws unauthorized error when invoke then return unauthorized error`() =
        runTest {
            val languageCode = "en"
            val exception = AltasheratException(NetworkError.UNAUTHORIZED)

            doAnswer { throw exception }.whenever(remoteDS).getTourismVisaRequests(languageCode)

            useCase(languageCode).test {
                assertThat(awaitItem()).isEqualTo(Resource.Loading)
                val errorResult = awaitItem() as Resource.Error
                assertThat(errorResult.error).isEqualTo(NetworkError.UNAUTHORIZED)
                awaitComplete()
            }

            verify(remoteDS, times(1)).getTourismVisaRequests(languageCode)
        }

    @Test
    fun `given valid language code and API throws unknown exception when invoke then return unknown error`() =
        runTest {
            val languageCode = "en"
            val exception = RuntimeException("Something unexpected happened")

            whenever(remoteDS.getTourismVisaRequests(languageCode)).thenThrow(exception)

            useCase(languageCode).test {
                assertThat(awaitItem()).isEqualTo(Resource.Loading)
                val errorResult = awaitItem() as Resource.Error
                assertThat(errorResult.error).isInstanceOf(AltasheratError.UnknownError::class.java)
                assertThat((errorResult.error as AltasheratError.UnknownError).unknownErrorMessage)
                    .contains("Unknown error in GetTourismVisaRequestsUC")
                awaitComplete()
            }

            verify(remoteDS, times(1)).getTourismVisaRequests(languageCode)
        }

    @Test
    fun `given valid language code and API returns null when invoke then return unknown error`() =
        runTest {
            val languageCode = "en"

            whenever(remoteDS.getTourismVisaRequests(languageCode)).thenReturn(null)

            useCase(languageCode).test {
                assertThat(awaitItem()).isEqualTo(Resource.Loading)
                val errorResult = awaitItem() as Resource.Error
                assertThat(errorResult.error).isInstanceOf(AltasheratError.UnknownError::class.java)
                assertThat((errorResult.error as AltasheratError.UnknownError).unknownErrorMessage)
                    .contains("Unknown error in GetTourismVisaRequestsUC")
                awaitComplete()
            }

            verify(remoteDS, times(1)).getTourismVisaRequests(languageCode)
        }


}