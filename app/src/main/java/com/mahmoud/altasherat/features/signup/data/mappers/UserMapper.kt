package com.mahmoud.altasherat.features.signup.data.mappers

import com.mahmoud.altasherat.features.signup.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.signup.data.models.dto.UserDto
import com.mahmoud.altasherat.features.signup.data.models.entity.UserEntity
import com.mahmoud.altasherat.features.signup.domain.models.User

internal object UserMapper {
    fun dtoToDomain(model: UserDto): User {
        return User(
            id = model.id ?: -1,
            username = model.userName.orEmpty(),
            firstname = model.firstName.orEmpty(),
            middleName = model.middleName.orEmpty(),
            lastname = model.lastName.orEmpty(),
            email = model.email.orEmpty(),
            phone = PhoneMapper.dtoToDomain(model.phone ?: PhoneDto()),
            image = model.image.orEmpty(),
            birthDate = model.birthDate.orEmpty(),
            emailVerified = model.emailVerified ?: false,
            phoneVerified = model.phoneVerified ?: false,
            isBlocked = model.isBlocked ?: 0
        )
    }

    fun entityToDomain(model: UserEntity): User {
        return User(
            id = model.id,
            username = model.username,
            firstname = model.firstname,
            middleName = model.middlename,
            lastname = model.lastname,
            email = model.email,
            phone = PhoneMapper.entityToDomain(model.phone),
            image = model.image,
            birthDate = model.birthDate,
            emailVerified = model.emailVerified,
            phoneVerified = model.phoneVerified,
            isBlocked = model.isBlocked
        )
    }

    fun domainToEntity(model: User): UserEntity {
        return UserEntity(
            id = model.id,
            username = model.username,
            firstname = model.firstname,
            middlename = model.middleName,
            lastname = model.lastname,
            email = model.email,
            phone = PhoneMapper.domainToEntity(model.phone),
            image = model.image,
            birthDate = model.birthDate,
            emailVerified = model.emailVerified,
            phoneVerified = model.phoneVerified,
            isBlocked = model.isBlocked
        )
    }
}