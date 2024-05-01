package com.fistr.fistr.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.generated.destinations.BrowseScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ChatsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.generated.destinations.RegisterScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SplashScreenDestination
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState {
    return remember(navController) {
        AppState(navController = navController, coroutineScope = coroutineScope)
    }
}

fun NavDestination?.shouldShowBottomNavigationOnThisDestinations() =
    when (this?.route) {
        HomeScreenDestination.route -> true
        BrowseScreenDestination.route -> true
        ChatsScreenDestination.route -> true
        else -> false
    }

class AppState(val navController: NavHostController, val coroutineScope: CoroutineScope) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val shouldShowBottomNavigation: Boolean
        @Composable get() = currentDestination.shouldShowBottomNavigationOnThisDestinations()
}
