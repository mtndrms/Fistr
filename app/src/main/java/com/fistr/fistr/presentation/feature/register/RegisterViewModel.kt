package com.fistr.fistr.presentation.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fistr.fistr.data.local.data_store.AppRepository
import com.fistr.fistr.domain.Result
import com.fistr.fistr.domain.asText
import com.fistr.fistr.domain.use_case.RegisterUserUseCase
import com.fistr.fistr.domain.validator.UserDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val userDataValidator = UserDataValidator()

    private fun onEmailValueChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    private fun onUsernameValueChange(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    private fun onPasswordValueChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    private fun validateUserData(email: String, username: String, password: String): Boolean {
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

    private fun onRegisterClick(email: String, username: String, password: String) {
        val isValid = validateUserData(email = email, username = username, password = password)
        if (isValid) {
            registerUserUseCase(
                email = email,
                username = username,
                password = password
            ).map { result ->
                when (result) {
                    is Result.Error -> _uiState.update {
                        it.copy(
                            registerStatus = false,
                            registerErrorMessage = result.error.asText()
                        )
                    }

                    is Result.Success -> {
                        appRepository.saveLoggedInUserLocally(
                            result.data.id,
                            result.data.username,
                            result.data.fullName
                        )

                        _uiState.update {
                            it.copy(
                                registerStatus = true,
                                registerErrorMessage = null
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailValueChange -> {
                onEmailValueChange(event.email)
            }

            is RegisterEvent.UsernameValueChange -> {
                onUsernameValueChange(event.username)
            }

            is RegisterEvent.PasswordValueChange -> {
                onPasswordValueChange(event.password)
            }

            RegisterEvent.RegisterClick -> {
                onRegisterClick(
                    email = uiState.value.email,
                    username = uiState.value.username,
                    password = uiState.value.password
                )
            }
        }
    }
}