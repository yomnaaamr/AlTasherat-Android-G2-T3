package com.mahmoud.altasherat.features.authentication.login.data.mappers

import com.mahmoud.altasherat.features.login.data.models.dto.LoginResponseDto
import com.mahmoud.altasherat.features.login.data.models.entity.LoginEntity
import com.mahmoud.altasherat.features.login.domain.models.Login

object LoginMapper :Mapper<LoginResponseDto , Login,LoginEntity> {
    override fun dtoToEntity(input: LoginResponseDto): LoginEntity {
        return LoginEntity(
            message = input.message,
            token = input.token,
            user = UserMapper.dtoToEntity(input.user)
        )
    }

    override fun dtoToDomain(input: LoginResponseDto): Login {
        return Login(
            message = input.message,
            token = input.token,
            user = UserMapper.dtoToDomain(input.user)
        )
    }

    override fun domainToEntity(input: Login): LoginEntity {
        return LoginEntity(
            message = input.message,
            token = input.token,
            user = UserMapper.domainToEntity(input.user)
        )
    }
}