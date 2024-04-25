package com.fistr.fistr.presentation.feature.register

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
class RegisterViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val userDataValidator = UserDataValidator()

    fun onEmailValueChange(email: String) = _uiState.update { it.copy(email = email) }

    fun onUsernameValueChange(username: String) = _uiState.update { it.copy(username = username) }

    fun onPasswordValueChange(password: String) = _uiState.update { it.copy(password = password) }

    fun onRegisterClick(email: String, username: String, password: String): Boolean {
        when (val result = userDataValidator.validateEmail(email)) {
            is Result.Error -> _uiState.update { it.copy(emailValidationMessage = result.error.asText()) }
            is Result.Success -> _uiState.update { it.copy(emailValidationMessage = null) }
        }

        when (val result = userDataValidator.validateUsername(username)) {
            is Result.Error -> _uiState.update { it.copy(usernameValidationMessage = result.error.asText()) }
            is Result.Success -> _uiState.update { it.copy(usernameValidationMessage = null) }
        }

        when (val result = userDataValidator.validatePassword(password)) {
            is Result.Error -> _uiState.update { it.copy(passwordValidationMessage = result.error.asText()) }
            is Result.Success -> _uiState.update { it.copy(passwordValidationMessage = null) }
        }

        return uiState.value.emailValidationMessage == null &&
                uiState.value.usernameValidationMessage == null &&
                uiState.value.passwordValidationMessage == null
    }
}