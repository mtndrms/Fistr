package com.fistr.fistr.data.model

data class Chat(
    val id: Int,
    val participants: List<User>,
    val preview: Message
)
