package com.fistr.fistr.presentation.feature.chat

import com.fistr.fistr.data.model.Message
import com.fistr.fistr.presentation.common.UiText

data class ChatUiState(
    var fullName: String = "",
    var message: String = "",
    val data: DataState,
)

sealed interface DataState {
    data object Loading : DataState
    data class LoadFailed(val errorMessage: UiText) : DataState
    data class Success(
        val messages: List<Message> = emptyList()
    ) : DataState
}
