package com.fistr.fistr.presentation.feature.splash

import android.os.CountDownTimer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavOptions
import com.fistr.fistr.R
import com.fistr.fistr.utils.avoidCreatingBackStackEntry
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SplashScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(start = true)
@Composable
fun SplashScreen(navigator: DestinationsNavigator) {
    Content(
        navigateToLoginScreen = {
            navigator.navigate(
                direction = LoginScreenDestination(),
                navOptions = NavOptions.Builder()
                    .avoidCreatingBackStackEntry(SplashScreenDestination.route)
                    .build()
            )
        },
        navigateToHomeScreen = {
            navigator.navigate(
                direction = HomeScreenDestination(),
                navOptions = NavOptions.Builder()
                    .avoidCreatingBackStackEntry(SplashScreenDestination.route)
                    .build()
            )
        }
    )
}

@Composable
private fun Content(
    navigateToLoginScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val iconState = remember { MutableTransitionState(false).apply { targetState = true } }
    val titleState = remember { MutableTransitionState(false).apply { targetState = true } }

    LaunchedEffect(key1 = true) {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                if (false) {
                    navigateToHomeScreen()
                } else {
                    navigateToLoginScreen()
                }
            }
        }.start()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AnimatedVisibility(
            visibleState = iconState,
            enter = scaleIn(animationSpec = tween(delayMillis = 500)),
            exit = scaleOut()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_boxing_gloves),
                contentDescription = "app icon"
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        AnimatedVisibility(
            visibleState = titleState,
            enter = expandHorizontally(animationSpec = tween(delayMillis = 500)),
            exit = ExitTransition.None
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 48.sp
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSplashScreen() {
    Content(navigateToLoginScreen = {}, navigateToHomeScreen = {})
}
