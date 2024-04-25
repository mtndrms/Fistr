package com.fistr.fistr.presentation.common.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.startDestination

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        content = {
            val currentDestination =
                navController.currentDestinationAsState().value
                    ?: NavGraphs.root.startDestination

            BottomBarDestination.entries.forEach { destination ->
                NavigationBarItem(
                    selected = currentDestination == destination.direction,
                    onClick = {
                        navController.navigate(
                            route = destination.direction.route,
                            navOptions = navOptions { launchSingleTop = false }
                        )
                    },
                    label = { Text(text = stringResource(id = destination.label)) },
                    icon = {
                        Icon(
                            imageVector = if (currentDestination == destination.direction) {
                                destination.iconSelected
                            } else {
                                destination.icon
                            },
                            contentDescription = stringResource(
                                id = destination.label
                            )
                        )
                    }
                )
            }
        }
    )
}