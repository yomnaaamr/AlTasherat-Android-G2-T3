package com.mahmoud.altasherat.features.profile_info.data.mappers

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.mappers.UserMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.dto.UserDto
import com.mahmoud.altasherat.features.profile_info.data.models.dto.UpdateAccDto
import com.mahmoud.altasherat.features.profile_info.data.models.entity.UpdateAccEntity
import com.mahmoud.altasherat.features.profile_info.domain.models.UpdateAcc

internal object UpdateAccMapper {
    fun dtoToDomain(model: UpdateAccDto): UpdateAcc {
        return UpdateAcc(
            message = model.message.orEmpty(),
            user = UserMapper.dtoToDomain(model.user ?: UserDto())
        )
    }

    fun entityToDomain(model: UpdateAccEntity): UpdateAcc {
        return UpdateAcc(
            message = model.message,
            user = model.user
        )
    }

    fun domainToEntity(model: UpdateAcc): UpdateAccEntity {
        return UpdateAccEntity(
            message = model.message,
            user = model.user
        )
    }
}