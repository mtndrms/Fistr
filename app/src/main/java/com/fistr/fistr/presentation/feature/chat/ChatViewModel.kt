package com.fistr.fistr.presentation.feature.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fistr.fistr.data.local.data_store.AppRepository
import com.fistr.fistr.domain.Result
import com.fistr.fistr.domain.asText
import com.fistr.fistr.domain.use_case.GetAllMessagesForChatUseCase
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
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appRepository: AppRepository,
    private val getAllMessagesForChatUseCase: GetAllMessagesForChatUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState(data = DataState.Loading))
    val uiState = _uiState.asStateFlow()

    private val navArgs: ChatScreenNavArgs = savedStateHandle.navArgs()

    init {
        viewModelScope.launch {
            val userID = appRepository.getLoggedInUserID()
            getAllMessagesForChat(userID = userID, chatID = navArgs.chatID)
            _uiState.update { it.copy(fullName = navArgs.fullName) }
        }
    }

    private fun onMessageValueChange(message: String) =
        _uiState.update { it.copy(message = message) }

    private fun getAllMessagesForChat(userID: Int, chatID: Int) {
        getAllMessagesForChatUseCase(userID, chatID).map { result ->
            when (result) {
                is Result.Error -> DataState.LoadFailed(result.error.asText())
                is Result.Success -> DataState.Success(result.data)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DataState.Loading
        ).onEach { newValue ->
            _uiState.update {
                it.copy(
                    data = when (newValue) {
                        is DataState.Loading -> newValue
                        is DataState.LoadFailed -> newValue
                        is DataState.Success -> newValue
                    }
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.EmojiPanelOpen -> {

            }

            is ChatEvent.MessageValueChange -> {
                onMessageValueChange(event.message)
            }

            is ChatEvent.Send -> {

            }
        }
    }
}
