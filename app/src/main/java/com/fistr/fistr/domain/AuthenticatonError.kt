package com.fistr.fistr.domain

import com.fistr.fistr.R
import com.fistr.fistr.presentation.common.UiText

enum class AuthenticatonError : Error {
    EMPTY_FIELD,
    INCORRECT_CREDENTIALS
}

fun AuthenticatonError.asText(): UiText {
    return when (this) {
        AuthenticatonError.EMPTY_FIELD -> UiText.StringResource(
            R.string.this_field_cannot_be_empty
        )

        AuthenticatonError.INCORRECT_CREDENTIALS -> UiText.StringResource(
            R.string.incorrect_login_credentials_please_try_again
        )
    }
}