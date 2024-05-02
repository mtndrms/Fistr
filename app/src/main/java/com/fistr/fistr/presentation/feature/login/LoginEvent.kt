package com.fistr.fistr.presentation.feature.login

sealed interface LoginEvent {
    data class UserIdentifierValueChange(val userIdentifier: String): LoginEvent
    data class PasswordValueChange(val password: String): LoginEvent
    data object LoginClick : LoginEvent
    data class ToggleKeepMeSignedIn(val value: Boolean): LoginEvent
}
