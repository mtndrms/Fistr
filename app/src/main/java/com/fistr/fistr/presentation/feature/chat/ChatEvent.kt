package com.fistr.fistr.presentation.feature.chat

sealed interface ChatEvent {
    data object Send : ChatEvent
    data object EmojiPanelOpen : ChatEvent
    data class MessageValueChange(val message: String) : ChatEvent
}
