package com.fistr.fistr.presentation.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fistr.fistr.R
import com.fistr.fistr.presentation.common.FistrIcons
import com.fistr.fistr.utils.preference.Language
import com.fistr.fistr.utils.preference.Theme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Content(
        onEvent = viewModel::onEvent,
        navigateBack = navigator::popBackStack,
        navigateToLoginScreen = { navigator.navigate(LoginScreenDestination) },
        uiState = uiState
    )
}

@Composable
private fun Content(
    onEvent: (SettingsEvent) -> Unit,
    navigateBack: () -> Unit,
    navigateToLoginScreen: () -> Unit,
    uiState: SettingsUiState,
    modifier: Modifier = Modifier
) {
    val themeDialogVisibilityState = remember { mutableStateOf(false) }
    val languageDialogVisibilityState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .then(modifier)
    ) {
        SettingsTopBar(navigateBack = navigateBack)
        uiState.userSettings?.let { userSettings ->
            SettingDialogItem(
                title = R.string.theme,
                currentlySelected = stringResource(id = Theme.entries[userSettings.selectedTheme].resID),
                onClick = { themeDialogVisibilityState.value = true }
            )
            SettingDialogItem(
                title = R.string.language,
                currentlySelected = stringResource(id = Language.entries[userSettings.selectedLanguage].resID),
                onClick = { languageDialogVisibilityState.value = true }
            )
            SettingTextItem(
                title = R.string.logout,
                textColor = MaterialTheme.colorScheme.error,
                onClick = { onEvent(SettingsEvent.LogOutEvent(navigateToLoginScreen = navigateToLoginScreen)) }
            )

            if (themeDialogVisibilityState.value) {
                DialogChooseTheme(
                    selectedThemeOption = userSettings.selectedTheme,
                    onThemeOptionSelected = { onEvent(SettingsEvent.ThemeOptionChange(id = it)) },
                    dismiss = { themeDialogVisibilityState.value = false }
                )
            }

            if (languageDialogVisibilityState.value) {
                DialogChooseLanguage(
                    selectedLanguageOption = userSettings.selectedLanguage,
                    onLanguageOptionSelected = { onEvent(SettingsEvent.LanguageOptionChange(id = it)) },
                    dismiss = { languageDialogVisibilityState.value = false }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsTopBar(navigateBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.settings))
        },
        navigationIcon = {
            Icon(
                imageVector = FistrIcons.back,
                contentDescription = stringResource(id = R.string.back),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(CircleShape)
                    .clickable { navigateBack() }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogChooseTheme(
    selectedThemeOption: Int,
    onThemeOptionSelected: (Int) -> Unit,
    dismiss: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { dismiss() },
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.theme),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Theme.entries.forEach { theme ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = theme.id == selectedThemeOption,
                        onClick = {
                            onThemeOptionSelected(theme.id)
                            dismiss()
                        }
                    )
                    Text(
                        text = stringResource(id = theme.resID),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogChooseLanguage(
    selectedLanguageOption: Int,
    onLanguageOptionSelected: (Int) -> Unit,
    dismiss: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { dismiss() },
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.language),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Language.entries.forEach { language ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = language.id == selectedLanguageOption,
                        onClick = {
                            onLanguageOptionSelected(language.id)
                            dismiss()
                        }
                    )
                    Text(
                        text = stringResource(id = language.resID),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingDialogItem(title: Int, currentlySelected: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable {
                onClick()
            }
            .padding(horizontal = 20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = title),
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = currentlySelected,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun SettingSwitchItem(checked: Boolean, title: Int, updateValue: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = title),
            maxLines = 1,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Switch(
            checked = checked,
            onCheckedChange = {
                updateValue()
            }
        )
    }
}

@Composable
private fun SettingTextItem(
    title: Int,
    onClick: () -> Unit,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                onClick()
            }
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = title),
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium,
            color = textColor
        )
    }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    Content(
        onEvent = {},
        navigateBack = {},
        navigateToLoginScreen = {},
        uiState = SettingsUiState(
            userSettings = UserSettings(
                selectedTheme = 0,
                selectedLanguage = 0
            ),
        )
    )
}
