package com.fistr.fistr.data.model

import com.fistr.fistr.data.mock_data.WeightClassType
import com.fistr.fistr.data.mock_data.StanceType

data class User(
    val id: Int,
    val username: String,
    val fullName: String,
    val weightClass: WeightClassType = WeightClassType.MIDDLEWEIGHT,
    val age: Int = 0,
    val height: Int = 0,
    val weight: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
    val stance: StanceType = StanceType.SWITCH,
    var expertises: List<MartialArt> = emptyList()
)
