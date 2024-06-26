package com.fistr.fistr.presentation.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fistr.fistr.data.local.data_store.AppRepository
import com.fistr.fistr.domain.Result
import com.fistr.fistr.domain.asText
import com.fistr.fistr.domain.use_case.GetUserUseCase
import com.ramcosta.composedestinations.generated.navArgs
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
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appRepository: AppRepository,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val navArgs: ProfileScreenNavArgs = savedStateHandle.navArgs()

    init {
        viewModelScope.launch {
            val loggedInUserID = appRepository.getLoggedInUserID()
            _uiState.update {
                it.copy(
                    isUsersOwnProfile = loggedInUserID == navArgs.userID
                )
            }

            getUserByID(navArgs.userID)
        }
    }

    private fun getUserByID(id: Int) {
        getUserUseCase(id = id).map { result ->
            when (result) {
                is Result.Error -> DataState.LoadFailed(message = result.error.asText())
                is Result.Success -> DataState.Success(user = result.data)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DataState.Loading
        ).onEach { dataState ->
            _uiState.update { it.copy(state = dataState) }
        }.launchIn(viewModelScope)
    }
}
