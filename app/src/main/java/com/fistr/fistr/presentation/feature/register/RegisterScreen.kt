package com.fistr.fistr.presentation.feature.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.fistr.fistr.utils.avoidCreatingBackStackEntry
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.RegisterScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Content(
        navigateToHomeScreen = {
            navigator.navigate(
                direction = HomeScreenDestination,
                navOptions = NavOptions.Builder()
                    .avoidCreatingBackStackEntry(RegisterScreenDestination.route)
                    .build()
            )
        },
        onEmailValueChange = viewModel::onEmailValueChange,
        onUsernameValueChange = viewModel::onUsernameValueChange,
        onPasswordValueChange = viewModel::onPasswordValueChange,
        onRegisterClick = viewModel::onRegisterClick,
        uiState = uiState
    )
}

@Composable
private fun Content(
    navigateToHomeScreen: () -> Unit,
    onEmailValueChange: (String) -> Unit,
    onUsernameValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onRegisterClick: (String, String, String) -> Boolean,
    uiState: RegisterUiState,
    modifier: Modifier = Modifier
) {
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
            EmailSection(
                isError = uiState.emailValidationMessage != null,
                errorMessage = uiState.emailValidationMessage?.asString() ?: "",
                value = uiState.email,
                onValueChanged = {
                    onEmailValueChange(it)
                }
            )
            UsernameSection(
                isError = uiState.usernameValidationMessage != null,
                errorMessage = uiState.usernameValidationMessage?.asString() ?: "",
                username = uiState.username,
                onValueChanged = {
                    onUsernameValueChange(it)
                }
            )
            PasswordSection(
                isError = uiState.passwordValidationMessage != null,
                errorMessage = uiState.passwordValidationMessage?.asString() ?: "",
                password = uiState.password,
                onValueChanged = {
                    onPasswordValueChange(it)
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    val isRegisterSuccessful = onRegisterClick(
                        uiState.email,
                        uiState.username,
                        uiState.password
                    )

                    if (isRegisterSuccessful) {
                        navigateToHomeScreen()
                    }
                },
                content = { Text(text = stringResource(R.string.register)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun Title() {
    Text(
        text = stringResource(
            R.string.login_and_register_screen_title_template,
            stringResource(id = R.string.register),
            stringResource(id = R.string.app_name)
        ),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun EmailSection(
    isError: Boolean,
    errorMessage: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            onValueChanged(input)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        placeholder = {
            Text(text = stringResource(R.string.e_mail))
        },
        leadingIcon = {
            Icon(
                imageVector = FistrIcons.email,
                contentDescription = stringResource(id = R.string.e_mail).lowercase(),
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
                    modifier = Modifier.clickable { onValueChanged("") },
                )
            }
        },
        isError = isError,
        supportingText = {
            Text(
                text = if (isError) errorMessage else ""
            )
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
private fun UsernameSection(
    isError: Boolean,
    errorMessage: String,
    username: String,
    onValueChanged: (String) -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = username,
        onValueChange = { value ->
            onValueChanged(value)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        placeholder = {
            Text(text = stringResource(R.string.username))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = FistrIcons.username),
                contentDescription = stringResource(id = R.string.username).lowercase(),
                tint = if (isError) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
        },
        trailingIcon = {
            if (username.isNotEmpty()) {
                Icon(
                    imageVector = FistrIcons.clear,
                    contentDescription = "clear",
                    tint = if (isError) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                    modifier = Modifier.clickable { onValueChanged("") }
                )
            }
        },
        isError = isError,
        supportingText = {
            Text(text = errorMessage)
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
    isError: Boolean,
    errorMessage: String,
    password: String,
    onValueChanged: (String) -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = password,
        onValueChange = { value ->
            onValueChanged(value)
        },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        placeholder = {
            Text(text = stringResource(R.string.password))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = FistrIcons.password),
                contentDescription = stringResource(id = R.string.password).lowercase(),
                tint = if (isError) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
        },
        trailingIcon = {
            if (password.isNotEmpty()) {
                Icon(
                    imageVector = FistrIcons.clear,
                    contentDescription = "clear",
                    tint = if (isError) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                    modifier = Modifier.clickable { onValueChanged("") },
                )
            }
        },
        isError = isError,
        supportingText = {
            Text(text = errorMessage)
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
@Preview
private fun PreviewRegisterScreen() {
    Content(
        navigateToHomeScreen = {},
        onEmailValueChange = {},
        onUsernameValueChange = {},
        onPasswordValueChange = {},
        onRegisterClick = { _, _, _ -> true },
        uiState = RegisterUiState(
            email = "john@doe.com",
            username = "john-doe",
            password = "123456Abc."
        )
    )
}