package com.fistr.fistr.presentation.feature.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fistr.fistr.data.local.data_store.AppRepository
import com.fistr.fistr.domain.Result
import com.fistr.fistr.domain.asText
import com.fistr.fistr.domain.use_case.GetUsersAllChatsUseCase
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
class ChatsViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val getUsersAllChatsUseCase: GetUsersAllChatsUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<ChatsUiState> = MutableStateFlow(ChatsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val userID = appRepository.getLoggedInUserID()
            getUsersAllChats(userID)
        }
    }

    private fun getUsersAllChats(userID: Int) {
        getUsersAllChatsUseCase(userID).map { result ->
            when (result) {
                is Result.Error -> ChatsUiState.LoadFailed(result.error.asText())
                is Result.Success -> ChatsUiState.Success(chats = result.data)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ChatsUiState.Loading
        ).onEach { newValue ->
            _uiState.update {
                when (newValue) {
                    is ChatsUiState.Loading -> newValue
                    is ChatsUiState.LoadFailed -> newValue
                    is ChatsUiState.Success -> newValue
                }
            }

            //or simply
            //_uiState.value = newValue
            //but this can lead an unexpected issues especially in multi-threaded env.
        }.launchIn(viewModelScope)
    }
}