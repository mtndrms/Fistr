package com.fistr.fistr.presentation.feature.chat

import com.fistr.fistr.data.model.Message
import com.fistr.fistr.presentation.common.UiText

sealed interface ChatUiState {
    data object Loading : ChatUiState
    data class LoadFailed(val errorMessage: UiText) : ChatUiState
    data class Success(
        val messages: List<Message> = emptyList()
    ) : ChatUiState
}
