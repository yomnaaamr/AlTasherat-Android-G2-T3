package com.mahmoud.altasherat.features.menu_options.change_password.domain.repository.remote

import com.mahmoud.altasherat.features.menu_options.change_password.data.models.dto.ChangePasswordDto
import com.mahmoud.altasherat.features.menu_options.change_password.data.models.request.ChangePassRequest

interface IChangePassRemoteDS {
    suspend fun changePassword(request: ChangePassRequest): ChangePasswordDto
}