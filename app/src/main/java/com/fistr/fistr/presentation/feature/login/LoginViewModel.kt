package com.fistr.fistr.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fistr.fistr.data.local.data_store.AppRepository
import com.fistr.fistr.data.local.data_store.SettingsRepository
import com.fistr.fistr.data.model.User
import com.fistr.fistr.domain.Result
import com.fistr.fistr.domain.asText
import com.fistr.fistr.domain.use_case.AuthenticateUserUseCase
import com.fistr.fistr.domain.validator.UserDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val appRepository: AppRepository,
    private val authenticateUserUseCase: AuthenticateUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val userDataValidator: UserDataValidator = UserDataValidator()

    init {
        checkIfKeepMeSignedInOn()
    }

    private fun checkIfKeepMeSignedInOn() {
        settingsRepository.isKeepMeSignedInOn.onEach { preference ->
            _uiState.update { it.copy(keepMeSignedIn = preference) }
        }.launchIn(viewModelScope)
    }

    private fun toogleKeepMeSignedIn(value: Boolean) {
        viewModelScope.launch {
            settingsRepository.toggleKeepMeSignedIn(value)
        }

        _uiState.update { it.copy(keepMeSignedIn = value) }
    }

    private fun onUserIdentifierValueChange(userIdentifier: String) {
        _uiState.update { it.copy(userIdentifier = userIdentifier) }
    }

    private fun onPasswordValueChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    private fun onLogInClick(userIdentifier: String, password: String) {
        when (
            val result = if (userIdentifier.contains("@")) {
                userDataValidator.validateEmail(userIdentifier)
            } else {
                userDataValidator.validateUsername(userIdentifier)
            }
        ) {
            is Result.Error -> _uiState.update { it.copy(userIdentifierErrorMessage = result.error.asText()) }
            is Result.Success -> _uiState.update { it.copy(userIdentifierErrorMessage = null) }
        }

        when (val result = userDataValidator.validatePassword(password)) {
            is Result.Error -> _uiState.update { it.copy(passwordErrorMessage = result.error.asText()) }
            is Result.Success -> _uiState.update { it.copy(passwordErrorMessage = null) }
        }

        if (uiState.value.userIdentifierErrorMessage == null && uiState.value.passwordErrorMessage == null) {
            authenticateUser(userIdentifier = userIdentifier, password = password)
        }
    }

    private fun authenticateUser(userIdentifier: String, password: String) {
        authenticateUserUseCase(username = userIdentifier, password = password).map { result ->
            when (result) {
                is Result.Error -> _uiState.update {
                    it.copy(
                        loginStatus = false,
                        loginErrorMessage = result.error.asText()
                    )
                }

                is Result.Success -> {
                    appRepository.saveLoggedInUserLocally(
                        id = result.data.id,
                        username = result.data.username,
                        fullName = result.data.fullName
                    )

                    _uiState.update {
                        it.copy(
                            loginStatus = true,
                            loginErrorMessage = null
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UserIdentifierValueChange -> {
                onUserIdentifierValueChange(event.userIdentifier)
            }

            is LoginEvent.PasswordValueChange -> {
                onPasswordValueChange(event.password)
            }

            is LoginEvent.ToggleKeepMeSignedIn -> {
                toogleKeepMeSignedIn(event.value)
            }

            is LoginEvent.LoginClick -> {
                onLogInClick(
                    userIdentifier = uiState.value.userIdentifier,
                    password = uiState.value.password
                )
            }
        }
    }
}
