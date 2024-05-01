package com.fistr.fistr.domain.use_case

import com.fistr.fistr.data.model.Chat
import com.fistr.fistr.domain.DataError
import com.fistr.fistr.domain.Result
import com.fistr.fistr.data.mock_data.FakeChatData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUsersAllChatsUseCase @Inject constructor() {
    operator fun invoke(userID: Int): Flow<Result<List<Chat>, DataError.Remote>> = flow {
        try {
            val chats = FakeChatData.getUsersAllChats(userID = userID)
            emit(Result.Success(chats))
        } catch (exception: Exception) {
            emit(Result.Error(DataError.Remote.UNKNOWN))
        }
    }
}