package com.fistr.fistr.domain.use_case

import com.fistr.fistr.data.mock_data.FakeUserData
import com.fistr.fistr.data.model.User
import com.fistr.fistr.domain.RegistrationError
import com.fistr.fistr.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor() {
    operator fun invoke(
        email: String,
        username: String,
        password: String
    ): Flow<Result<User, RegistrationError>> = flow {
        try {
            when (
                val result = FakeUserData.register(
                    email = email,
                    username = username,
                    password = password,
                    fullName = username
                )
            ) {
                is Result.Error -> emit(Result.Error(result.error))
                is Result.Success -> {
                    emit(Result.Success(
                        result.data.run {
                            User(
                                id = id,
                                username = username,
                                fullName = fullName
                            )
                        }
                    ))
                }
            }

        } catch (exception: Exception) {
            emit(Result.Error(RegistrationError.UNKNOWN))
        }
    }
}