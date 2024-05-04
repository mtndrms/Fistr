package com.fistr.fistr.data.mock_data

import com.fistr.fistr.data.mock_data.model.Stance
import com.fistr.fistr.data.mock_data.model.UserEntity
import com.fistr.fistr.data.model.MartialArt
import com.fistr.fistr.data.model.User
import com.fistr.fistr.domain.AuthenticatonError
import com.fistr.fistr.domain.RegistrationError
import com.fistr.fistr.domain.Result

//This class fakes a remote database
object FakeUserData {
    private val user1 = UserEntity(
        id = 1,
        email = "test@user.com",
        username = "test1",
        password = "123456Abc.",
        fullName = "Test User",
        weightClass = WeightClass.MIDDLEWEIGHT,
        age = 28,
        height = 174,
        weight = 70,
        wins = 15,
        losses = 3,
        stance = Stance.SWITCH,
        expertises = listOf(
            FakeMartialArtData.MartialArt.MMA,
            FakeMartialArtData.MartialArt.JUDO,
            FakeMartialArtData.MartialArt.AIKIDO
        )
    )
    private val user2 = UserEntity(
        id = 2,
        email = "test@user2.com",
        username = "test2",
        password = "223456Abc.",
        fullName = "Test User 2",
        weightClass = WeightClass.WELTERWEIGHT,
        age = 24,
        height = 172,
        weight = 72,
        wins = 10,
        losses = 2,
        stance = Stance.ORTHODOX,
        expertises = listOf(
            FakeMartialArtData.MartialArt.KRAV_MAGA,
            FakeMartialArtData.MartialArt.BOXING,
            FakeMartialArtData.MartialArt.MUAY_THAI,
            FakeMartialArtData.MartialArt.TAEKWONDO
        )
    )
    private val user3 = UserEntity(
        id = 3,
        email = "test@user3.com",
        username = "test3",
        password = "323456Abc.",
        fullName = "Test User 3",
        weightClass = WeightClass.LIGHTWEIGHT,
        age = 31,
        height = 176,
        weight = 68,
        wins = 20,
        losses = 5,
        stance = Stance.ORTHODOX,
        expertises = listOf(
            FakeMartialArtData.MartialArt.JIU_JITSU,
            FakeMartialArtData.MartialArt.KRAV_MAGA,
            FakeMartialArtData.MartialArt.KUNG_FU,
            FakeMartialArtData.MartialArt.KARATE,
            FakeMartialArtData.MartialArt.BOXING,
            FakeMartialArtData.MartialArt.MUAY_THAI,
            FakeMartialArtData.MartialArt.AIKIDO,
            FakeMartialArtData.MartialArt.MMA
        )
    )
    private val user4 = UserEntity(
        id = 4,
        email = "test@user4.com",
        username = "test4",
        password = "423456Abc.",
        fullName = "Test User 4",
        weightClass = WeightClass.FEATHERWEIGHT,
        age = 26,
        height = 175,
        weight = 72,
        wins = 8,
        losses = 1,
        stance = Stance.SOUTHPAW,
        expertises = listOf(
            FakeMartialArtData.MartialArt.BOXING
        )
    )

    private val users = mutableListOf(user1, user2, user3, user4)

    fun getAllUsers() = users
    fun getUserByID(id: Int): User {
        return users[id - 1].run {
            User(
                id = this.id,
                username = username,
                fullName = fullName,
                weightClass = weightClass,
                age = age,
                height = height,
                weight = weight,
                stance = stance,
                wins = wins,
                losses = losses,
                expertises = expertises.map {
                    MartialArt(
                        id = it.id,
                        name = it.martialArtName
                    )
                }
            )
        }
    }

    fun getUserByUsername(username: String) = users.any { it.username == username }
    fun register(
        email: String,
        username: String,
        password: String,
        fullName: String
    ): Result<UserEntity, RegistrationError> {
        val isEmailAlreadyUsed = users.any { it.email == email }
        if (isEmailAlreadyUsed) {
            return Result.Error(RegistrationError.DUPLICATE_EMAIL)
        }

        val isUsernameTaken = users.any { it.username == username }
        if (isUsernameTaken) {
            return Result.Error(RegistrationError.DUPLICATE_USERNAME)
        }

        val newUser = UserEntity(
            id = users.size + 1,
            email = email,
            username = username,
            password = password,
            fullName = fullName,
            weightClass = WeightClass.MIDDLEWEIGHT,
            age = 0,
            height = 0,
            weight = 0,
            wins = 0,
            losses = 0,
            stance = Stance.SWITCH,
            expertises = emptyList()
        )

        users.add(newUser)
        return Result.Success(newUser)
    }

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
