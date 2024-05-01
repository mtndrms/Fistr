package com.fistr.fistr.data.mock_data

import com.fistr.fistr.data.model.Chat
import com.fistr.fistr.data.model.Message

object FakeChatData {
    private val chat1 = Chat(
        id = 1,
        participants = listOf(FakeUserData.getUserByID(1), FakeUserData.getUserByID(2)),
        preview = Message(
            id = 1,
            chatID = 1,
            senderID = 1,
            content = "Message 1 for chat 1 from User 1",
            timestamp = "10:00"
        )
    )

    private val chat2 = Chat(
        id = 2,
        participants = listOf(FakeUserData.getUserByID(1), FakeUserData.getUserByID(3)),
        preview = Message(
            id = 7,
            chatID = 2,
            senderID = 1,
            content = "Message 7 for chat 2 from User 1",
            timestamp = "10:04"
        )
    )

    private val chat3 = Chat(
        id = 3,
        participants = listOf(FakeUserData.getUserByID(1), FakeUserData.getUserByID(4)),
        preview = Message(
            id = 4,
            chatID = 3,
            senderID = 4,
            content = "Message 4 for chat 3 from User 4",
            timestamp = "11:11"
        )
    )

    private val chat4 = Chat(
        id = 4,
        participants = listOf(FakeUserData.getUserByID(2), FakeUserData.getUserByID(4)),
        preview = Message(
            id = 4,
            chatID = 3,
            senderID = 4,
            content = "Message 1 for chat 4 from User 4",
            timestamp = "12:12"
        )
    )

    private val chats = listOf(chat1, chat2, chat3, chat4)

    fun getAllChats() = chats
    fun getUsersAllChats(userID: Int) = chats.filter { it.participants[0].id == userID }
    fun getChatByID(id: Int) = chats[id - 1]
}
