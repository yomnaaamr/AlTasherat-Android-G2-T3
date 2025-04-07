package com.mahmoud.altasherat.features.authentication.login.data.mappers

import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.UserMapper
import com.mahmoud.altasherat.features.authentication.login.data.models.dto.LoginResponseDto
import com.mahmoud.altasherat.features.authentication.login.data.models.entity.LoginEntity
import com.mahmoud.altasherat.features.authentication.login.domain.models.Login

object LoginMapper {

    fun dtoToDomain(input: LoginResponseDto): Login {
        return Login(
            message = input.message,
            token = input.token,
            user = UserMapper.dtoToDomain(input.user)
        )
    }

    fun domainToEntity(input: Login): LoginEntity {
        return LoginEntity(
            message = input.message,
            token = input.token,
            user = UserMapper.domainToEntity(input.user)
        )
    }
}