package com.fistr.fistr.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.fistr.fistr.presentation.common.bottom_bar.BottomNavigationBar
import com.fistr.fistr.presentation.feature.login.LoginScreen
import com.fistr.fistr.presentation.theme.AppTheme
import com.fistr.fistr.utils.SetSystemBarColors
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()
            val appState = rememberAppState(navController = navController)
            val snackbarHostState = remember { SnackbarHostState() }

            AppTheme {
                SetSystemBarColors(
                    statusBarColor = appState.systemBarColors.first,
                    navigationBarColor = appState.systemBarColors.second,
                    darkTheme = isSystemInDarkTheme()
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Scaffold(
                        bottomBar = {
                            if (appState.shouldShowBottomNavigation) {
                                BottomNavigationBar(navController = navController)
                            }
                        },
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState)
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { paddingValues: PaddingValues ->
                        DestinationsNavHost(
                            navController = navController,
                            navGraph = NavGraphs.root,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            composable(LoginScreenDestination) {
                                LoginScreen(
                                    navigator = destinationsNavigator,
                                    showSnackbar = { options ->
                                        scope.launch {
                                            if (options.actionLabel != null) {
                                                val result = snackbarHostState.showSnackbar(
                                                    message = options.message,
                                                    actionLabel = options.actionLabel,
                                                    duration = options.duration
                                                )

                                                when (result) {
                                                    SnackbarResult.Dismissed -> {
                                                        options.onDismissed()
                                                    }

                                                    SnackbarResult.ActionPerformed -> {
                                                        options.onActionPerformed()
                                                    }
                                                }
                                            } else {
                                                snackbarHostState.showSnackbar(options.message)
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
