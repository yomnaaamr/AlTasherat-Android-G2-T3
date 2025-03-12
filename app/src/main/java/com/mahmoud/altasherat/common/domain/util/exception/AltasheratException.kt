package com.mahmoud.altasherat.common.domain.util.exception

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError

class AltasheratException(
    val error: AltasheratError,
): Exception()