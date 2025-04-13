package com.mahmoud.altasherat.features.tourism_visa.data.mappers

import com.mahmoud.altasherat.features.tourism_visa.data.models.dto.TourismVisaResponseDto
import com.mahmoud.altasherat.features.tourism_visa.domain.models.TourismVisaResponse

internal object TourismVisaResponseMapper {
    fun dtoToDomain(model: TourismVisaResponseDto): TourismVisaResponse {
        return TourismVisaResponse(
            message = model.message,
            tourismVisa = TourismVisaMapper.dtoToDomain(model.tourismVisa)
        )
    }
}