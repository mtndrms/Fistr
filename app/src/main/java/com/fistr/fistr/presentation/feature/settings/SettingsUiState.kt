package com.fistr.fistr.presentation.feature.settings

data class SettingsUiState(
    val userSettings: UserSettings? = null
)

data class UserSettings(
    var selectedTheme: Int = -1,
    var selectedLanguage: Int = -1,
)
