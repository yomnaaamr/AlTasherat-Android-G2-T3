package com.mahmoud.altasherat.features.delete_account.delete_acc.data.mappers

import com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.dto.DeleteAccDto
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.models.DeleteAcc

internal object DeleteAccMapper {
    fun dtoToDomain(model: DeleteAccDto): DeleteAcc {
        return DeleteAcc(
            message = model.message
        )
    }

}