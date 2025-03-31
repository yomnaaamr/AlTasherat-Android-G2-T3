package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.mappers

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.dto.ImageDto
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.entity.ImageEntity
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.Image

internal object ImageMapper {
    fun dtoToDomain(model: ImageDto): Image {
        return Image(
            id = model.id ?: -1,
            type = model.type.orEmpty(),
            path = model.path.orEmpty(),
            title = model.title.orEmpty(),
            description = model.description.orEmpty(),
            priority = model.priority ?: -1,
            main = model.main ?: false,
            createdAt = model.createdAt.orEmpty(),
            updatedAt = model.updatedAt.orEmpty()
        )
    }

    fun entityToDomain(model: ImageEntity): Image {
        return Image(
            id = model.id,
            type = model.type,
            path = model.path,
            title = model.title,
            description = model.description,
            priority = model.priority,
            main = model.main,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt
        )
    }

    fun domainToEntity(model: Image): ImageEntity {
        return ImageEntity(
            id = model.id ?: -1,
            type = model.type.orEmpty(),
            path = model.path.orEmpty(),
            title = model.title.orEmpty(),
            description = model.description.orEmpty(),
            priority = model.priority ?: -1,
            main = model.main ?: false,
            createdAt = model.createdAt.orEmpty(),
            updatedAt = model.updatedAt.orEmpty()
        )
    }
}