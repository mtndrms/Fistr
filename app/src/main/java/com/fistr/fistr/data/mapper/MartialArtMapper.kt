package com.fistr.fistr.data.mapper

import com.fistr.fistr.data.mock_data.MartialArtType
import com.fistr.fistr.data.model.MartialArt

fun MartialArtType.toMartialArt(): MartialArt {
    return MartialArt(
        id = this.id,
        name = this.martialArtName
    )
}

fun List<MartialArtType>.toMartialArtList(): List<MartialArt> {
    return this.map {
        it.toMartialArt()
    }
}