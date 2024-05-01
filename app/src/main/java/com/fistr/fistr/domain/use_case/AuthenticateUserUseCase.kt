package com.fistr.fistr.domain.use_case

import com.fistr.fistr.data.mock_data.FakeUserData
import com.fistr.fistr.data.model.User
import com.fistr.fistr.domain.AuthenticatonError
import com.fistr.fistr.domain.Result
import com.fistr.fistr.domain.asText
import com.fistr.fistr.presentation.common.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticateUserUseCase @Inject constructor() {
    operator fun invoke(
        username: String,
        password: String
    ): Flow<Result<User, AuthenticatonError>> = flow {
        try {
            when (
                val authenticatedUser = FakeUserData.authUser(
                    username = username,
                    password = password
                )
            ) {
                is Result.Error -> emit(Result.Error(authenticatedUser.error))
                is Result.Success -> {
                    authenticatedUser.data.run {
                        val user = User(
                            id = id,
                            username = username,
                            fullName = fullName
                        )
                        emit(Result.Success(user))
                    }
                }
            }
        } catch (exception: Exception) {
            emit(Result.Error(AuthenticatonError.INCORRECT_CREDENTIALS))
        }
    }
}
