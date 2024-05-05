package com.fistr.fistr.utils.preference

import androidx.annotation.StringRes
import com.fistr.fistr.R

enum class Language(val id: Int, @StringRes val resID: Int) {
    SYSTEM_DEFAULT(id = 0, resID = R.string.system_default),
    ENGLISH(id = 1, resID = R.string.english),
    TURKISH(id = 2, resID = R.string.turkish),
    GERMAN(id = 3, resID = R.string.german),
    RUSSIAN(id = 4, resID = R.string.russian)
}
