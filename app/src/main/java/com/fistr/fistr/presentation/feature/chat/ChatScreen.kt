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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
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
import com.fistr.fistr.presentation.feature.profile.ProfileScreenNavArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

@Destination<RootGraph>(navArgs = ChatScreenNavArgs::class)
@Composable
fun ChatScreen(navigator: DestinationsNavigator, viewModel: ChatViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = LocalLifecycleOwner.current)

    Content(
        navigateToProfile = { navigator.navigate(it) },
        navigateToBack = navigator::popBackStack,
        onEvent = viewModel::onEvent,
        uiState = uiState
    )
}

@Composable
private fun Content(
    navigateToProfile: (Direction) -> Unit,
    navigateToBack: () -> Unit,
    onEvent: (ChatEvent) -> Unit,
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
        ChatTopBar(
            userID = uiState.userID,
            fullName = uiState.fullName,
            navigateToProfile = navigateToProfile,
            navigateToBack = navigateToBack,
            onMoreClick = { }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .shadow(1.dp)
                .alpha(0.01f)
                .background(MaterialTheme.colorScheme.onBackground)
        )
        when (uiState.data) {
            is DataState.Loading -> LoadingState()
            is DataState.LoadFailed -> LoadFailedState(
                uiState = uiState.data,
                modifier = Modifier.weight(1f)
            )

            is DataState.Success -> SuccessState(
                uiState = uiState.data,
                modifier = Modifier.weight(1f)
            )
        }
        ComposeField(
            uiState = uiState.message,
            onEmojiPanelOpenClick = { onEvent(ChatEvent.EmojiPanelOpen) },
            onSendClick = { onEvent(ChatEvent.Send) },
            onMessageValueChange = { onEvent(ChatEvent.MessageValueChange(it)) })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatTopBar(
    userID: Int,
    fullName: String,
    navigateToProfile: (Direction) -> Unit,
    navigateToBack: () -> Unit,
    onMoreClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    navigateToProfile(
                        ProfileScreenDestination(ProfileScreenNavArgs(userID = userID))
                    )
                }
            ) {
                Image(
                    imageVector = FistrIcons.person,
                    contentDescription = "profile picture",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceContainer),
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            shape = CircleShape
                        )
                        .padding(5.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = fullName)
            }
        },
        navigationIcon = {
            Icon(
                imageVector = FistrIcons.back,
                contentDescription = stringResource(id = R.string.back),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clip(CircleShape)
                    .clickable { navigateToBack() }
            )
        },
        actions = {
            Icon(
                imageVector = FistrIcons.more,
                contentDescription = stringResource(id = R.string.more),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(CircleShape)
                    .clickable { onMoreClick() }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ComposeField(
    uiState: String,
    onEmojiPanelOpenClick: () -> Unit,
    onSendClick: () -> Unit,
    onMessageValueChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(horizontal = 15.dp)
    ) {
        Image(
            painter = painterResource(id = FistrIcons.emoji),
            contentDescription = "emoji panel open",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer),
            modifier = Modifier
                .clip(CircleShape)
                .clickable { onEmojiPanelOpenClick() }
        )
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedTextField(
            value = uiState,
            onValueChange = {
                onMessageValueChange(it)
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            placeholder = { Text(text = stringResource(id = R.string.message)) },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            imageVector = FistrIcons.send,
            contentDescription = "send",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer),
            modifier = Modifier
                .clip(CircleShape)
                .clickable { onSendClick() }
        )
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = Modifier.then(modifier))
}

@Composable
private fun LoadFailedState(uiState: DataState.LoadFailed, modifier: Modifier = Modifier) {
    Text(
        text = uiState.errorMessage.asString(),
        modifier = Modifier.then(modifier)
    )
}

@Composable
private fun SuccessState(uiState: DataState.Success, modifier: Modifier = Modifier) {
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
        navigateToProfile = {},
        navigateToBack = {},
        onEvent = {},
        uiState = ChatUiState(
            fullName = "John Doe",
            data = DataState.Success(FakeMessageData.getAllMessagesForChat(2))
        )
    )
}
