package com.mahmoud.altasherat.common.domain.util.error

interface AltasheratError {

    data class UnknownError(val unknownErrorMessage: String) : AltasheratError

    data class UnknownServerError(val code: Int) : AltasheratError

    data class ValidationErrors(val errors: List<ValidationError>) : AltasheratError

}