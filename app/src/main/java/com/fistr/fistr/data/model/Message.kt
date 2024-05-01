package com.fistr.fistr.data.model

data class Message(
    val id: Int,
    val chatID: Int,
    val senderID: Int,
    val content: String,
    val timestamp: String,
    var isLoggedInUserOwnThisMessage: Boolean = false
)
