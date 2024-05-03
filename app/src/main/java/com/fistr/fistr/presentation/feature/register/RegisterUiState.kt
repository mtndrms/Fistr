package com.fistr.fistr.presentation.feature.register

import com.fistr.fistr.presentation.common.UiText

data class RegisterUiState(
    var email: String = "",
    var username: String = "",
    var password: String = "",
    var emailValidationMessage: UiText? = null,
    var usernameValidationMessage: UiText? = null,
    var passwordValidationMessage: UiText? = null,
    var registerStatus: Boolean = false,
    var registerErrorMessage: UiText? = null,
)
