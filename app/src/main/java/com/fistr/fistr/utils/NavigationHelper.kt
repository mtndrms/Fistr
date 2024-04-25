package com.fistr.fistr.utils

import androidx.navigation.NavOptions

fun NavOptions.Builder.avoidCreatingBackStackEntry(route: String): NavOptions.Builder {
    setPopUpTo(route = route, inclusive = true)
    setLaunchSingleTop(true)

    return this
}