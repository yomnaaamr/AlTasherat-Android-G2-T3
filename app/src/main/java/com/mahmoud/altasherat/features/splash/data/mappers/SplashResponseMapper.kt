package com.mahmoud.altasherat.features.splash.data.mappers

import com.mahmoud.altasherat.features.splash.data.models.dto.SplashResponseDto
import com.mahmoud.altasherat.features.splash.data.models.entity.SplashEntity
import com.mahmoud.altasherat.features.splash.domain.models.SplashResponse

internal object SplashResponseMapper {

    fun dtoToDomain(model: SplashResponseDto): SplashResponse {
        return SplashResponse(
            data = model.data?.map { CountryMapper.dtoToDomain(it) } ?: emptyList()
        )
    }

    fun domainToEntity(model: SplashResponse): SplashEntity {
        return SplashEntity(
            data = model.data.map { CountryMapper.domainToEntity(it) }
        )
    }
}