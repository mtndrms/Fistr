package com.fistr.fistr.presentation.feature.register

sealed interface RegisterEvent {
    data class EmailValueChange(val email: String) : RegisterEvent
    data class UsernameValueChange(val username: String) : RegisterEvent
    data class PasswordValueChange(val password: String) : RegisterEvent
    data object RegisterClick : RegisterEvent
}
