package com.fistr.fistr.presentation.feature.chats

import com.fistr.fistr.data.model.Chat
import com.fistr.fistr.presentation.common.UiText

sealed interface ChatsUiState {
    data object Loading : ChatsUiState
    data class LoadFailed(val errorMessage: UiText) : ChatsUiState
    data class Success(
        val chats: List<Chat>
    ) : ChatsUiState
}
