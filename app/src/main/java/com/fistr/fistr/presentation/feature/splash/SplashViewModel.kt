package com.fistr.fistr.presentation.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fistr.fistr.data.local.data_store.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val settingsRepository: SettingsRepository): ViewModel() {
    val isKeepMeSignedInOnState = MutableStateFlow(false)

    init {
        isKeepMeSignedInOn()
    }

    private fun isKeepMeSignedInOn() {
        viewModelScope.launch {
            val isKeepMeSignedInOn = settingsRepository.isKeepMeSignedInOn()
            isKeepMeSignedInOnState.value = isKeepMeSignedInOn
        }
    }
}
