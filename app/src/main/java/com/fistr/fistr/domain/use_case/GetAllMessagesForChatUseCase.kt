package com.fistr.fistr.domain.use_case

import com.fistr.fistr.data.model.Message
import com.fistr.fistr.domain.DataError
import com.fistr.fistr.domain.Result
import com.fistr.fistr.data.mock_data.FakeMessageData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllMessagesForChatUseCase @Inject constructor() {
    operator fun invoke(userID: Int, chatID: Int): Flow<Result<List<Message>, DataError.Remote>> =
        flow {
            try {
                val messages = FakeMessageData.getAllMessagesForChat(chatID = chatID)
                emit(
                    Result.Success(
                        data = messages.map {
                            it.isLoggedInUserOwnThisMessage = userID == it.senderID
                            it
                        }
                    )
                )
            } catch (exception: Exception) {
                emit(Result.Error(DataError.Remote.UNKNOWN))
            }
        }
}
