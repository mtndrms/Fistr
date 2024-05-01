package com.fistr.fistr.utils

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavOptions
import androidx.compose.ui.graphics.Color

fun NavOptions.Builder.avoidCreatingBackStackEntry(route: String): NavOptions.Builder {
    setPopUpTo(route = route, inclusive = true)
    setLaunchSingleTop(true)

    return this
}

@Composable
fun SetSystemBarColors(statusBarColor: Color, navigationBarColor: Color, darkTheme: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = statusBarColor.toArgb()
            window.navigationBarColor = navigationBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).run {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }
}
