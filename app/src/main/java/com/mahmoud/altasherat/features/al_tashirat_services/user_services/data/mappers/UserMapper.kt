package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.mappers

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.dto.ImageDto
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.dto.UserDto
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.entity.UserEntity
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.Image
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User


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
            image = ImageMapper.dtoToDomain(model.image ?: ImageDto()),
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
            image = ImageMapper.entityToDomain(model.image),
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
            middlename = model.middleName.orEmpty(),
            lastname = model.lastname,
            email = model.email,
            phone = PhoneMapper.domainToEntity(model.phone),
            image = ImageMapper.domainToEntity(model.image ?: Image()),
            birthDate = model.birthDate.orEmpty(),
            emailVerified = model.emailVerified,
            phoneVerified = model.phoneVerified,
            isBlocked = model.isBlocked
        )
    }
}