package com.fistr.fistr.data.mock_data

//This class fakes a remote database
object FakeMartialArtData {
    enum class MartialArt(val id: Int, val martialArtName: String) {
        KARATE(0, "Karate"),
        JUDO(1, "Judo"),
        TAEKWONDO(2, "Taekwondo"),
        KUNG_FU(3, "Kung Fu"),
        AIKIDO(4, "Aikido"),
        JIU_JITSU(5, "Jiu Jitsu"),
        MUAY_THAI(6, "Muay Thai"),
        KRAV_MAGA(7, "Krav Maga"),
        MMA(8, "Mixed Martial Arts"),
        BOXING(9, "Boxing"),
    }

    val martialArts = MartialArt.entries
}