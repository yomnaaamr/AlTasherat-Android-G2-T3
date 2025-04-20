package com.mahmoud.altasherat.features.update_account.data.mappers

import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.UserMapper
import com.mahmoud.altasherat.features.update_account.data.models.dto.UpdateAccDto
import com.mahmoud.altasherat.features.update_account.data.models.entity.UpdateAccEntity
import com.mahmoud.altasherat.features.update_account.domain.models.UpdateAcc

internal object UpdateAccMapper {
    fun dtoToDomain(model: UpdateAccDto): UpdateAcc {
        return UpdateAcc(
            message = model.message.orEmpty(),
            user = UserMapper.dtoToDomain(model.user!!)
        )
    }

    fun entityToDomain(model: UpdateAccEntity): UpdateAcc {
        return UpdateAcc(
            message = model.message,
            user = UserMapper.entityToDomain(model.user)
        )
    }

    fun domainToEntity(model: UpdateAcc): UpdateAccEntity {
        return UpdateAccEntity(
            message = model.message,
            user = UserMapper.domainToEntity(model.user)
        )
    }
}