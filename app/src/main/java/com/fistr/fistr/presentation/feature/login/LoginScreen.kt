package com.fistr.fistr.presentation.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import com.fistr.fistr.R
import com.fistr.fistr.presentation.common.FistrIcons
import com.fistr.fistr.presentation.common.SnackbarOptions
import com.fistr.fistr.utils.avoidCreatingBackStackEntry
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.generated.destinations.RegisterScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun LoginScreen(
    showSnackbar: (SnackbarOptions) -> Unit,
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Content(
        navigateToHomeScreen = {
            navigator.navigate(
                direction = HomeScreenDestination(),
                navOptions = NavOptions.Builder()
                    .avoidCreatingBackStackEntry(LoginScreenDestination.route)
                    .build()
            )
        },
        navigateToRegisterScreen = {
            navigator.navigate(direction = RegisterScreenDestination())
        },
        onUserIdentiferValueChange = viewModel::onUserIdentifierValueChange,
        onLogInClick = viewModel::onLogInClick,
        onPasswordValueChange = viewModel::onPasswordValueChange,
        toogleKeepMeSignedIn = viewModel::toogleKeepMeSignedIn,
        showSnackbar = showSnackbar,
        uiState = uiState
    )
}

@Composable
private fun Content(
    navigateToHomeScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    onUserIdentiferValueChange: (String) -> Unit,
    onLogInClick: (username: String, password: String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    toogleKeepMeSignedIn: (Boolean) -> Unit,
    showSnackbar: (SnackbarOptions) -> Unit,
    uiState: LoginUiState,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.loginStatus, key2 = uiState.loginErrorMessage) {
        if (uiState.loginStatus && uiState.loginErrorMessage == null) {
            navigateToHomeScreen()
        } else {
            uiState.loginErrorMessage?.let {
                showSnackbar(SnackbarOptions(message = it.asString(context)))
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.75f)
        ) {
            Title()
            Spacer(modifier = Modifier.height(25.dp))
            UserIdentifierSection(
                value = uiState.userIdentifier,
                isError = uiState.userIdentifierErrorMessage != null,
                errorMessage = uiState.userIdentifierErrorMessage?.asString() ?: "",
                onValueChange = onUserIdentiferValueChange
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordSection(
                value = uiState.password,
                isError = uiState.passwordErrorMessage != null,
                errorMessage = uiState.passwordErrorMessage?.asString() ?: "",
                onValueChange = onPasswordValueChange
            )
            KeepMeSignedIn(toogleKeepMeSignedIn = toogleKeepMeSignedIn, uiState = uiState)
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                content = { Text(text = stringResource(R.string.log_in)) },
                onClick = { onLogInClick(uiState.userIdentifier, uiState.password) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Don't have an account.")
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Create one.",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { navigateToRegisterScreen() }
                )
            }
        }
    }
}

@Composable
private fun Title() {
    Text(
        text = stringResource(
            id = R.string.login_and_register_screen_title_template,
            stringResource(id = R.string.log_in),
            stringResource(id = R.string.app_name)
        ),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun UserIdentifierSection(
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        placeholder = {
            Text(text = stringResource(R.string.username_or_e_mail))
        },
        leadingIcon = {
            Icon(
                imageVector = FistrIcons.person,
                contentDescription = stringResource(R.string.username_or_e_mail),
                tint = if (isError) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    imageVector = FistrIcons.clear,
                    contentDescription = "clear",
                    tint = if (isError) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                    modifier = Modifier.clickable { onValueChange("") }
                )
            }
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = errorMessage)
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            errorPlaceholderColor = MaterialTheme.colorScheme.error
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun PasswordSection(
    value: String,
    isError: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        placeholder = {
            Text(text = stringResource(id = R.string.password))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = FistrIcons.password),
                contentDescription = stringResource(id = R.string.password),
                tint = if (isError) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    imageVector = FistrIcons.clear,
                    contentDescription = "clear",
                    tint = if (isError) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                    modifier = Modifier.clickable { onValueChange("") }
                )
            }
        },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = errorMessage)
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            errorPlaceholderColor = MaterialTheme.colorScheme.error
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun KeepMeSignedIn(
    uiState: LoginUiState,
    toogleKeepMeSignedIn: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        CompositionLocalProvider(
            LocalMinimumInteractiveComponentEnforcement provides false
        ) {
            Checkbox(
                checked = uiState.keepMeSignedIn,
                onCheckedChange = { toogleKeepMeSignedIn(it) })
        }
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = stringResource(R.string.keep_me_signed_in))
    }
}

@Composable
@Preview
private fun PreviewLoginScreen() {
    Content(
        navigateToHomeScreen = {},
        navigateToRegisterScreen = {},
        onUserIdentiferValueChange = {},
        onLogInClick = { _, _ -> },
        onPasswordValueChange = {},
        toogleKeepMeSignedIn = {},
        showSnackbar = {_ -> },
        uiState = LoginUiState(
            userIdentifier = "john-doe",
            password = "123456Abc."
        )
    )
}
