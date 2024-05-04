package com.fistr.fistr.data.mock_data.model

import com.fistr.fistr.data.mock_data.FakeMartialArtData
import com.fistr.fistr.data.mock_data.WeightClass

data class UserEntity(
    val id: Int,
    val email: String,
    val username: String,
    val password: String,
    val fullName: String,
    val weightClass: WeightClass,
    val age: Int,
    val height: Int,
    val weight: Int,
    val wins: Int,
    val losses: Int,
    val stance: Stance,
    val expertises: List<FakeMartialArtData.MartialArt>
)
