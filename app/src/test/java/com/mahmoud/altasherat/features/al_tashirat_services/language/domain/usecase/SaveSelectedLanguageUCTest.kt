package com.mahmoud.altasherat.features.al_tashirat_services.language.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import com.mahmoud.altasherat.features.al_tashirat_services.language.data.MockLanguageData.language
import com.mahmoud.altasherat.features.al_tashirat_services.language.data.repository.LanguageRepository
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models.Language
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
class SaveSelectedLanguageUCTest {
    private lateinit var saveSelectedLanguageUC: SaveSelectedLanguageUC
    private lateinit var languageRepository: LanguageRepository

    private val languageLocalDS: ILanguageLocalDS = mock()

    @Before
    fun setUp() {
        languageRepository = LanguageRepository(languageLocalDS)
        saveSelectedLanguageUC = SaveSelectedLanguageUC(languageRepository)
    }

    @Test
    fun `saveSelectedLanguage returns success when language is saved successfully`() = runTest {
        // Given
        val language = Language(0, "Arabic", code = "ar", flag = "🇸🇦",  isSelected = false,
            phoneCode = "+20")

        whenever(languageLocalDS.saveSelectedLanguage(language)).thenReturn(Unit)

        // When
        val flow = saveSelectedLanguageUC(language)

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(Resource.Success(Unit))
            awaitComplete()
        }

        verify(languageLocalDS, times(1)).saveSelectedLanguage(language)
    }

    @Test
    fun `getLanguageCode returns unknown error when exception is thrown`() = runTest {
        // Given
        val expectedException = AltasheratException(AltasheratError.UnknownError("Test Error"))

        whenever(languageLocalDS.saveSelectedLanguage(language)).thenAnswer { throw expectedException }

        // When
        val flow = saveSelectedLanguageUC(language)

        // Then
        flow.test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            val errorResult = awaitItem() as Resource.Error
            assertThat(errorResult.error).isEqualTo(AltasheratError.UnknownError("Test Error"))

            awaitComplete()
        }

        verify(languageLocalDS, times(1)).saveSelectedLanguage(language)
    }
}