package com.fistr.fistr.presentation.feature.login

import com.fistr.fistr.presentation.common.UiText

data class LoginUiState(
    var userIdentifier: String = "",
    var password: String = "",
    var userIdentifierErrorMessage: UiText? = null,
    var passwordErrorMessage: UiText? = null,
    var keepMeSignedIn: Boolean = false,
    var loginStatus: Boolean = false,
    var loginErrorMessage: UiText? = null,
)
