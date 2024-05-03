package com.fistr.fistr.domain

import com.fistr.fistr.R
import com.fistr.fistr.presentation.common.UiText

enum class RegistrationError : Error{
    DUPLICATE_EMAIL,
    DUPLICATE_USERNAME,
    UNKNOWN
}

fun RegistrationError.asText(): UiText {
    return when(this) {
        RegistrationError.DUPLICATE_EMAIL -> UiText.StringResource(R.string.duplicate_email)
        RegistrationError.DUPLICATE_USERNAME -> UiText.StringResource(R.string.duplicate_username)
        RegistrationError.UNKNOWN -> UiText.StringResource(R.string.unknown_error)
    }
}
