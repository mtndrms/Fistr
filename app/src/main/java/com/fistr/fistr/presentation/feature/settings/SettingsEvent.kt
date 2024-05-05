package com.fistr.fistr.presentation.feature.settings

sealed interface SettingsEvent {
    data class ThemeOptionChange(val id: Int) : SettingsEvent
    data class LanguageOptionChange(val id: Int) : SettingsEvent
    data class LogOutEvent(val navigateToLoginScreen: () -> Unit) : SettingsEvent
}
