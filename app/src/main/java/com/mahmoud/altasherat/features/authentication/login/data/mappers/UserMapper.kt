package com.mahmoud.altasherat.features.authentication.login.data.mappers

import com.mahmoud.altasherat.features.login.data.models.dto.UserDto
import com.mahmoud.altasherat.features.login.data.models.entity.UserEntity
import com.mahmoud.altasherat.features.login.domain.models.User

object UserMapper:Mapper<UserDto , User,UserEntity> {
    override fun dtoToEntity(input: UserDto): UserEntity {
        return UserEntity(
            all_permissions = input.all_permissions,
            birthdate = input.birthdate,
            blocked = input.blocked,
            country = CountryMapper.dtoToEntity(input.country),
            email = input.email,
            email_verified = input.email_verified,
            image = input.image,
            id = input.id,
            firstname = input.firstname,
            lastname = input.lastname,
            phone = com.mahmoud.altasherat.features.authentication.login.data.mappers.PhoneMapper.dtoToEntity(input.phone),
            username = input.username,
            middlename = input.middlename,
            phone_verified = input.phone_verified
        )
    }

    override fun dtoToDomain(input: UserDto): User {
        return User(
            all_permissions = input.all_permissions,
            birthdate = input.birthdate,
            blocked = input.blocked,
            country = CountryMapper.dtoToDomain(input.country),
            email = input.email,
            email_verified = input.email_verified,
            image = input.image,
            id = input.id,
            firstname = input.firstname,
            lastname = input.lastname,
            phone = com.mahmoud.altasherat.features.authentication.login.data.mappers.PhoneMapper.dtoToDomain(input.phone),
            username = input.username,
            middlename = input.middlename,
            phone_verified = input.phone_verified
        )
    }

    override fun domainToEntity(input: User): UserEntity {
        return UserEntity(
            all_permissions = input.all_permissions,
            birthdate = input.birthdate,
            blocked = input.blocked,
            country = CountryMapper.domainToEntity(input.country),
            email = input.email,
            email_verified = input.email_verified,
            image = input.image,
            id = input.id,
            firstname = input.firstname,
            lastname = input.lastname,
            phone = com.mahmoud.altasherat.features.authentication.login.data.mappers.PhoneMapper.domainToEntity(input.phone),
            username = input.username,
            middlename = input.middlename,
            phone_verified = input.phone_verified
        )
    }
}