package com.fistr.fistr.domain.use_case

import com.fistr.fistr.data.mock_data.FakeUserData
import com.fistr.fistr.data.model.User
import com.fistr.fistr.domain.DataError
import com.fistr.fistr.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor() {
    operator fun invoke(id: Int): Flow<Result<User, DataError.Remote>> = flow {
        try {
            val user = FakeUserData.getUserByID(id)
            emit(Result.Success(user))
        } catch (exception: Exception) {
            emit(Result.Error(DataError.Remote.UNKNOWN))
        }
    }
}