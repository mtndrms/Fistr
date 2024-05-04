package com.fistr.fistr.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.generated.destinations.BrowseScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ChatScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ChatsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
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

@Composable
fun NavDestination?.systemBarColorsAccordingToCurrentlyDisplayingScreen(): Pair<Color, Color> {
    return when (this?.route) {
        SplashScreenDestination.route -> {
            Pair(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.background
            )
        }

        HomeScreenDestination.route -> {
            Pair(
                MaterialTheme.colorScheme.surfaceContainer,
                MaterialTheme.colorScheme.secondaryContainer
            )
        }

        ProfileScreenDestination.route -> {
            Pair(
                MaterialTheme.colorScheme.surfaceContainer,
                MaterialTheme.colorScheme.background
            )
        }

        SettingsScreenDestination.route -> {
            Pair(
                MaterialTheme.colorScheme.surfaceContainer,
                MaterialTheme.colorScheme.background
            )
        }

        ChatScreenDestination.route -> {
            Pair(
                MaterialTheme.colorScheme.surfaceContainer,
                MaterialTheme.colorScheme.background
            )
        }

        else -> Pair(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.secondaryContainer
        )
    }
}

class AppState(val navController: NavHostController, val coroutineScope: CoroutineScope) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val shouldShowBottomNavigation: Boolean
        @Composable get() = currentDestination.shouldShowBottomNavigationOnThisDestinations()

    val systemBarColors: Pair<Color, Color>
        @Composable get() = currentDestination.systemBarColorsAccordingToCurrentlyDisplayingScreen()
}
