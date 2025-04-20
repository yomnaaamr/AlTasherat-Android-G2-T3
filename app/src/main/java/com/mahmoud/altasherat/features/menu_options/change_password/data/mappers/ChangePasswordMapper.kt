package com.mahmoud.altasherat.features.menu_options.change_password.data.mappers

import com.mahmoud.altasherat.features.menu_options.change_password.data.models.dto.ChangePasswordDto
import com.mahmoud.altasherat.features.menu_options.change_password.domain.models.ChangePassword


internal object ChangePasswordMapper {
    fun dtoToDomain(model: ChangePasswordDto?): ChangePassword {
        return ChangePassword(
            message = model?.message ?: "",
        )
    }

}