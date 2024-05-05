package com.fistr.fistr.utils.preference

import androidx.annotation.StringRes
import com.fistr.fistr.R

enum class Theme(val id: Int, @StringRes val resID: Int) {
    SYSTEM_DEFAULT(id = 0, resID = R.string.system_default),
    DARK(id = 1, resID = R.string.dark),
    LIGHT(id = 2, resID = R.string.light)
}