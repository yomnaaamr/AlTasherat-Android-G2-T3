package com.mahmoud.altasherat.features.menu_options.change_password.data.repository

import com.mahmoud.altasherat.features.menu_options.change_password.data.mappers.ChangePasswordMapper
import com.mahmoud.altasherat.features.menu_options.change_password.data.models.request.ChangePassRequest
import com.mahmoud.altasherat.features.menu_options.change_password.domain.models.ChangePassword
import com.mahmoud.altasherat.features.menu_options.change_password.domain.repository.IChangePassRepository
import com.mahmoud.altasherat.features.menu_options.change_password.domain.repository.remote.IChangePassRemoteDS


class ChangePassRepository(
    private val changePassRemoteDS: IChangePassRemoteDS
) : IChangePassRepository {
    override suspend fun changePassword(request: ChangePassRequest): ChangePassword {
        return ChangePasswordMapper.dtoToDomain(
            changePassRemoteDS.changePassword(
                request
            )
        )
    }
}