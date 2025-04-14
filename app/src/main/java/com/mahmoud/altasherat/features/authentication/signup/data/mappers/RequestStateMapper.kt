package com.mahmoud.altasherat.features.authentication.signup.data.mappers

import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.StatusDto
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.Status

object RequestStateMapper {


     fun dtoToDomain(input: StatusDto): Status {
        return Status(
            id = input.id ?: 0,
            name = input.name.orEmpty(),
            color = input.color.orEmpty(),
            activatedAt = input.activatedAt.orEmpty(),
            main = input.main ?: false,
            active = input.active ?: false
        )
    }
}