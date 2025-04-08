package com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.NetworkError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.country.data.MockCountryData
import com.mahmoud.altasherat.features.al_tashirat_services.country.data.repository.CountryRepository
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.ICountryRepository
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.local.ICountryLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.remote.ICountryRemoteDS
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
 * Unit tests for the [GetCountriesFromRemoteUC] class.
 *
 * This test class verifies the behavior of the [GetCountriesFromRemoteUC] under various scenarios,
 * including happy paths and error cases. It uses Mockito to mock the [ICountryRemoteDS] and [ICountryLocalDS]
 * dependency to isolate the use case logic. The tests leverage Turbine for testing Flows.
 *
 * The tests cover:
 * - Happy path scenarios (successful retrieval of countries).
 * - Error scenarios (unexpected exceptions, specific [AltasheratError]).
 */
@RunWith(MockitoJUnitRunner::class)
class GetCountriesFromRemoteUCTest {

    @Mock
    private lateinit var remoteDS: ICountryRemoteDS

    @Mock
    private lateinit var localDS: ICountryLocalDS

    private lateinit var repository: ICountryRepository

    private lateinit var useCase: GetCountriesFromRemoteUC

    @Before
    fun setUp() {
        repository = CountryRepository(remoteDS, localDS)
        useCase = GetCountriesFromRemoteUC(repository)
    }

    @Test
    fun `given valid language code when invoke then return countries successfully`() = runTest {
        val languageCode = "en"
        val expectedCountries = MockCountryData.validCountriesDto
        val expectedCountriesDomain = MockCountryData.validCountries

        whenever(remoteDS.getCountries(languageCode)).thenReturn(expectedCountries)

        useCase(languageCode).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val successResult = awaitItem() as Resource.Success
            assertThat(successResult.data).isEqualTo(expectedCountriesDomain)
            awaitComplete()
        }

        verify(remoteDS, times(1)).getCountries(languageCode)
    }

    @Test
    fun `given repository throws exception when invoke then return unknown error`() = runTest {
        val languageCode = "en"
        val exception = RuntimeException("Test Exception")

        whenever(remoteDS.getCountries(languageCode)).thenThrow(exception)

        useCase(languageCode).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isInstanceOf(AltasheratError.UnknownError::class.java)
            awaitComplete()
        }

        verify(remoteDS, times(1)).getCountries(languageCode)
    }

    @Test
    fun `given repository throws network error when invoke then return network error`() = runTest {
        val languageCode = "en"
        val exception = AltasheratException(NetworkError.NO_INTERNET)

        doAnswer { throw exception }.whenever(remoteDS).getCountries(languageCode)

        useCase(languageCode).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isEqualTo(NetworkError.NO_INTERNET)
            awaitComplete()
        }

        verify(remoteDS, times(1)).getCountries(languageCode)
    }

    @Test
    fun `given repository throws specific AltasheratError when invoke then return that error`() =
        runTest {
            val languageCode = "en"
            val exception = AltasheratException(AltasheratError.UnknownError("Test Error"))

            doAnswer { throw exception }.whenever(remoteDS).getCountries(languageCode)

            useCase(languageCode).test {
                assertThat(awaitItem()).isEqualTo(Resource.Loading)
                val errorResult = awaitItem() as Resource.Error
                assertThat(errorResult.error).isEqualTo(AltasheratError.UnknownError("Test Error"))
                awaitComplete()
            }

            verify(remoteDS, times(1)).getCountries(languageCode)
        }


}