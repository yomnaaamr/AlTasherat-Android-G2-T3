package com.mahmoud.altasherat.features.change_password.domain.repository

import com.mahmoud.altasherat.features.change_password.data.models.request.ChangePassRequest
import com.mahmoud.altasherat.features.change_password.domain.models.ChangePassword

interface IChangePassRepository {
    suspend fun changePassword(request: ChangePassRequest): ChangePassword
}