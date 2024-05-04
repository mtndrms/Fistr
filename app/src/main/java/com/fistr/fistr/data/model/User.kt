package com.fistr.fistr.data.model

import com.fistr.fistr.data.mock_data.WeightClass
import com.fistr.fistr.data.mock_data.model.Stance

data class User(
    val id: Int,
    val username: String,
    val fullName: String,
    val weightClass: WeightClass = WeightClass.MIDDLEWEIGHT,
    val age: Int = 0,
    val height: Int = 0,
    val weight: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
    val stance: Stance = Stance.SWITCH,
    var expertises: List<MartialArt> = emptyList()
)
