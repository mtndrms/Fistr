package com.fistr.fistr.presentation.feature.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fistr.fistr.R
import com.fistr.fistr.data.mock_data.FakeMessageData
import com.fistr.fistr.presentation.common.FistrIcons
import com.fistr.fistr.presentation.feature.chat.components.ItemMessage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(navArgs = ChatScreenNavArgs::class)
@Composable
fun ChatScreen(navigator: DestinationsNavigator, viewModel: ChatViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Content(navigateToBack = navigator::popBackStack, uiState = uiState)
}

@Composable
private fun Content(
    navigateToBack: () -> Unit,
    uiState: ChatUiState,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 10.dp)
            .then(modifier)
    ) {
        ChatTopBar(navigateToBack = navigateToBack)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .shadow(1.dp)
                .alpha(0.05f)
                .background(MaterialTheme.colorScheme.onBackground)
        )
        when (uiState) {
            is ChatUiState.Loading -> LoadingState()
            is ChatUiState.LoadFailed -> LoadFailedState(
                uiState = uiState,
                modifier = Modifier.weight(1f)
            )

            is ChatUiState.Success -> SuccessState(
                uiState = uiState,
                modifier = Modifier.weight(1f)
            )
        }
        ComposeField()
    }
}

@Composable
private fun ChatTopBar(navigateToBack: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .clickable { navigateToBack() }
        ) {
            Image(imageVector = FistrIcons.back, contentDescription = "go back")
            Image(
                imageVector = FistrIcons.person,
                contentDescription = stringResource(id = R.string.profile),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary),
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        shape = CircleShape
                    )
                    .padding(5.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "John Doe",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            imageVector = FistrIcons.more,
            contentDescription = stringResource(R.string.more)
        )
    }
}

@Composable
private fun ComposeField() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 15.dp)
    ) {
        Image(
            painter = painterResource(id = FistrIcons.emoji),
            contentDescription = stringResource(id = R.string.profile),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
            modifier = Modifier.clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            placeholder = { Text(text = stringResource(id = R.string.message)) },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            imageVector = FistrIcons.send,
            contentDescription = stringResource(id = R.string.profile),
            modifier = Modifier.clip(CircleShape)
        )
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = Modifier.then(modifier))
}

@Composable
private fun LoadFailedState(uiState: ChatUiState.LoadFailed, modifier: Modifier = Modifier) {
    Text(
        text = uiState.errorMessage.asString(),
        modifier = Modifier.then(modifier)
    )
}

@Composable
private fun SuccessState(uiState: ChatUiState.Success, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 5.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(items = uiState.messages) { message ->
                ItemMessage(
                    content = message.content,
                    timestamp = message.timestamp,
                    isLoggedInUserOwnThisMessage = message.isLoggedInUserOwnThisMessage
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewChatScreen() {
    Content(
        navigateToBack = {},
        uiState = ChatUiState.Success(FakeMessageData.getAllMessagesForChat(2))
    )
}
