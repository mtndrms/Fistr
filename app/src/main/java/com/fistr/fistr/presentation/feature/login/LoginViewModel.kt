package com.fistr.fistr.presentation.feature.login

import androidx.lifecycle.ViewModel
import com.fistr.fistr.domain.Result
import com.fistr.fistr.domain.asText
import com.fistr.fistr.domain.validator.UserDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val userDataValidator: UserDataValidator = UserDataValidator()

    fun onUserIdentifierValueChange(userIdentifier: String) {
        _uiState.update { it.copy(userIdentifier = userIdentifier) }
    }

    fun onPasswordValueChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onLogInClick(userIdentifier: String, password: String): Boolean {
        when (
            val result = if (userIdentifier.contains("@")) {
                userDataValidator.validateEmail(userIdentifier)
            } else {
                userDataValidator.validateUsername(userIdentifier)
            }
        ) {
            is Result.Error -> _uiState.update { it.copy(userIdentifierError = result.error.asText()) }
            is Result.Success -> _uiState.update { it.copy(userIdentifierError = null) }
        }

        when (val result = userDataValidator.validatePassword(password)) {
            is Result.Error -> _uiState.update { it.copy(passwordError = result.error.asText()) }
            is Result.Success -> _uiState.update { it.copy(passwordError = null) }
        }

        return uiState.value.userIdentifierError == null && uiState.value.passwordError == null
    }
}