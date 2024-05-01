package com.fistr.fistr.data.mock_data

import com.fistr.fistr.data.mock_data.model.UserEntity
import com.fistr.fistr.data.model.User
import com.fistr.fistr.domain.AuthenticatonError
import com.fistr.fistr.domain.Result

//This class fakes a remote API
object FakeUserData {
    private val user1 = UserEntity(id = 1, "test1", "123456Abc.", "Test User")
    private val user2 = UserEntity(id = 2, "test2", "223456Abc.", "Test User 2")
    private val user3 = UserEntity(id = 3, "test3", "323456Abc.", "Test User 3")
    private val user4 = UserEntity(id = 4, "test4", "423456Abc.", "Test User 4")

    private val users = listOf(user1, user2, user3, user4)

    fun getAllUsers() = users
    fun getUserByID(id: Int): User {
        return users[id - 1].run {
            User(
                id = this.id,
                username = username,
                fullName = fullName
            )
        }
    }
    fun getUserByUsername(username: String) = users.any { it.username == username }
    fun authUser(username: String, password: String): Result<UserEntity, AuthenticatonError> {
        val user = users.find { user -> user.username == username }
        user?.let { foundUser ->
            val isPasswordCorrect = foundUser.password == password
            return if (isPasswordCorrect) {
                Result.Success(foundUser)
            } else {
                Result.Error(AuthenticatonError.INCORRECT_CREDENTIALS)
            }
        } ?: run {
            return Result.Error(AuthenticatonError.INCORRECT_CREDENTIALS)
        }
    }
}