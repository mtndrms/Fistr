package com.fistr.fistr.presentation.feature.login

import com.fistr.fistr.presentation.common.UiText

data class LoginUiState(
    var userIdentifier: String = "",
    var password: String = "",
    var userIdentifierError: UiText? = null,
    var passwordError: UiText? = null
)
