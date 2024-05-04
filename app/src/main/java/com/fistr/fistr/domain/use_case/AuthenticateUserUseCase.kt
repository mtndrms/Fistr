package com.fistr.fistr.domain.use_case

import com.fistr.fistr.data.mock_data.FakeUserData
import com.fistr.fistr.data.model.MartialArt
import com.fistr.fistr.data.model.User
import com.fistr.fistr.domain.AuthenticatonError
import com.fistr.fistr.domain.Result
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
                val result = FakeUserData.authUser(
                    username = username,
                    password = password
                )
            ) {
                is Result.Error -> emit(Result.Error(result.error))
                is Result.Success -> {
                    result.data.run {
                        emit(
                            Result.Success(
                                User(
                                    id = id,
                                    username = username,
                                    fullName = fullName,
                                    expertises = expertises.map {
                                        MartialArt(
                                            id = it.id,
                                            name = it.martialArtName
                                        )
                                    }
                                )
                            )
                        )
                    }
                }
            }
        } catch (exception: Exception) {
            emit(Result.Error(AuthenticatonError.INCORRECT_CREDENTIALS))
        }
    }
}
