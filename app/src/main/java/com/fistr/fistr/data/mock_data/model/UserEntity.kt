package com.fistr.fistr.data.mock_data.model

import com.fistr.fistr.data.mock_data.MartialArtType
import com.fistr.fistr.data.mock_data.StanceType
import com.fistr.fistr.data.mock_data.WeightClassType

data class UserEntity(
    val id: Int,
    val email: String,
    val username: String,
    val password: String,
    val fullName: String,
    val weightClass: WeightClassType,
    val age: Int,
    val height: Int,
    val weight: Int,
    val wins: Int,
    val losses: Int,
    val stance: StanceType,
    val expertises: List<MartialArtType>
)
