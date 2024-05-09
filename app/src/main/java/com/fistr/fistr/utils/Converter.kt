package com.fistr.fistr.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.dpToPx(): Float {
    val density = LocalDensity.current.density
    return this.value * density
}