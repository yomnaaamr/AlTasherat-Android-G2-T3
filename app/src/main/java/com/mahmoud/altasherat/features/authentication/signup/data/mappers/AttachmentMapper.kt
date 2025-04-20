package com.mahmoud.altasherat.features.authentication.signup.data.mappers

import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.AttachmentDto
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.Attachment

object AttachmentMapper {

    fun dtoToDomain(input: AttachmentDto): Attachment {
        return Attachment(
            id = input.id ?: 0,
            type = input.type.orEmpty(),
            path = input.path.orEmpty(),
            title = input.title.orEmpty(),
            description = input.description.orEmpty(),
            priority = input.priority ?: 0,
            main = input.main ?: false,
            createdAt = input.createdAt.orEmpty(),
            updatedAt = input.updatedAt.orEmpty()
        )
    }
}