package com.fistr.fistr.presentation.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fistr.fistr.data.local.data_store.AppRepository
import com.fistr.fistr.data.local.data_store.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val appRepository: AppRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getSelectedSettings()
    }

    private fun getSelectedSettings() {
        viewModelScope.launch {
            val userSettings = UserSettings(
                selectedTheme = settingsRepository.getAppTheme(),
                selectedLanguage = settingsRepository.getAppLanguage()
            )

            _uiState.update {
                it.copy(userSettings = userSettings)
            }
        }
    }

    private fun changeAppTheme(id: Int) {
        viewModelScope.launch {
            settingsRepository.changeAppTheme(id)
            _uiState.update { it.copy(userSettings = it.userSettings?.copy(selectedTheme = id)) }
        }
    }

    private fun changeAppLanguage(id: Int) {
        viewModelScope.launch {
            settingsRepository.changeAppLanguage(id)
            _uiState.update { it.copy(userSettings = it.userSettings?.copy(selectedLanguage = id)) }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ThemeOptionChange -> {
                changeAppTheme(id = event.id)
            }

            is SettingsEvent.LanguageOptionChange -> {
                changeAppLanguage(id = event.id)
            }

            is SettingsEvent.LogOutEvent -> {
                viewModelScope.launch {
                    settingsRepository.clear()
                    appRepository.clear()
                    event.navigateToLoginScreen()
                }
            }
        }
    }
}
