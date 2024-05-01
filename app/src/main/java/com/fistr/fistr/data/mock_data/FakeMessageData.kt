package com.fistr.fistr.data.mock_data

import com.fistr.fistr.data.model.Message

object FakeMessageData {
    private val messages = listOf(
        Message(
            id = 1,
            chatID = 1,
            senderID = 1,
            content = "Message 1 for chat 1 from User 1",
            timestamp = "10:00"
        ),
        Message(
            id = 1,
            chatID = 2,
            senderID = 1,
            content = "Message 1 for chat 2 from User 1",
            timestamp = "10:00"
        ),
        Message(
            id = 2,
            chatID = 2,
            senderID = 3,
            content = "Message 2 for chat 2 from User 3",
            timestamp = "10:01"
        ),
        Message(
            id = 3,
            chatID = 2,
            senderID = 3,
            content = "Message 3 for chat 2 from User 3",
            timestamp = "10:01"
        ),
        Message(
            id = 4,
            chatID = 2,
            senderID = 1,
            content = "Message 4 for chat 2 from User 1",
            timestamp = "10:02"
        ),
        Message(
            id = 5,
            chatID = 2,
            senderID = 3,
            content = "Message 5 for chat 2 from User 3",
            timestamp = "10:03"
        ),
        Message(
            id = 6,
            chatID = 2,
            senderID = 1,
            content = "Message 6 for chat 2 from User 1",
            timestamp = "10:04"
        ),
        Message(
            id = 7,
            chatID = 2,
            senderID = 1,
            content = "Message 7 for chat 2 from User 1",
            timestamp = "10:04"
        ),
        Message(
            id = 1,
            chatID = 3,
            senderID = 1,
            content = "Message 1 for chat 3 from User 1",
            timestamp = "11:00"
        ),
        Message(
            id = 2,
            chatID = 3,
            senderID = 1,
            content = "Message 2 for chat 3 from User 1",
            timestamp = "11:00"
        ),
        Message(
            id = 3,
            chatID = 3,
            senderID = 1,
            content = "Message 3 for chat 3 from User 1",
            timestamp = "11:00"
        ),
        Message(
            id = 4,
            chatID = 3,
            senderID = 4,
            content = "Message 4 for chat 3 from User 4",
            timestamp = "11:11"
        )
    )

    fun getAllMessagesForChat(chatID: Int): List<Message> = messages.filter { message -> message.chatID == chatID }
}
