package com.mahmoud.altasherat.features.al_tashirat_services.language.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.language.data.repository.LanguageRepository
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.repository.local.ILanguageLocalDS
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
class GetLanguageCodeUCTest {

    private lateinit var getLanguageCodeUC: GetLanguageCodeUC
    private lateinit var languageRepository: LanguageRepository

    private val languageLocalDS: ILanguageLocalDS = mock()

    @Before
    fun setUp() {
        languageRepository = LanguageRepository(languageLocalDS)
        getLanguageCodeUC = GetLanguageCodeUC(languageRepository)
    }

    @Test
    fun `getLanguageCode returns language code when found`() = runTest {
        // Given
        whenever(languageLocalDS.getLanguageCode()).thenReturn("en")

        // When
        val flow = getLanguageCodeUC()

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(Resource.Success("en"))
            awaitComplete()
        }

        verify(languageLocalDS, times(1)).getLanguageCode()
    }

    @Test
    fun `getLanguageCode returns null when no language code is stored`() = runTest {
        // Given
        whenever(languageLocalDS.getLanguageCode()).thenReturn(null)

        // When
        val flow = getLanguageCodeUC()

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(Resource.Success(null))
            awaitComplete()
        }

        verify(languageLocalDS, times(1)).getLanguageCode()
    }

    @Test
    fun `getLanguageCode returns unknown error when exception is thrown`() = runTest {
        // Given
        val expectedException = AltasheratException(AltasheratError.UnknownError("Test Error"))

        whenever(languageLocalDS.getLanguageCode()).thenAnswer { throw expectedException }

        // When
        val flow = getLanguageCodeUC()

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isEqualTo(AltasheratError.UnknownError("Test Error"))

            awaitComplete()
        }

        verify(languageLocalDS, times(1)).getLanguageCode()
    }

}