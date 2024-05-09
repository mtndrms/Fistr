package com.fistr.fistr.data.mapper

import com.fistr.fistr.data.mock_data.model.UserEntity
import com.fistr.fistr.data.model.User

fun UserEntity.toUser(): User {
    return User(
        id = this.id,
        username = this.username,
        fullName = this.fullName,
        weightClass = this.weightClass,
        age = this.age,
        height = this.height,
        weight = this.weight,
        wins = this.wins,
        losses = this.losses,
        stance = this.stance,
        expertises = this.expertises.toMartialArtList()
    )
}

fun List<UserEntity>.toUserList(): List<User> {
    return this.map {
        it.toUser()
    }
}