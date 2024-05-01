package com.fistr.fistr.presentation.feature.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fistr.fistr.data.local.data_store.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val settingsRepository: SettingsRepository): ViewModel() {
    val isKeepMeSignedInOnState = MutableStateFlow(false)

    init {
        isKeepMeSignedInOn()
    }

    private fun isKeepMeSignedInOn() {
        settingsRepository.isKeepMeSignedInOn.onEach { preference ->
            isKeepMeSignedInOnState.value = preference
        }.launchIn(viewModelScope)
    }
}