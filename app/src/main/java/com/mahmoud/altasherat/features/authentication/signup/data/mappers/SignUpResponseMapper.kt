package com.mahmoud.altasherat.features.authentication.signup.data.mappers

import com.mahmoud.altasherat.features.signup.data.models.dto.SignUpResponseDto
import com.mahmoud.altasherat.features.signup.data.models.entity.SignUpEntity
import com.mahmoud.altasherat.features.signup.domain.models.SignUp

internal object SignUpResponseMapper {
    fun dtoToDomain(model: SignUpResponseDto): SignUp {
        return SignUp(
            message = model.message.orEmpty(),
            token = model.token.orEmpty(),
            user = UserMapper.dtoToDomain(model.user ?: com.mahmoud.altasherat.features.authentication.signup.data.models.dto.UserDto())
        )
    }

    fun entityToDomain(model: SignUpEntity): SignUp {
        return SignUp(
            message = model.message,
            token = model.token,
            user = UserMapper.entityToDomain(model.user)
        )
    }

    fun domainToEntity(model: SignUp): SignUpEntity {
        return SignUpEntity(
            message = model.message,
            token = model.token,
            user = UserMapper.domainToEntity(model.user)
        )
    }
}