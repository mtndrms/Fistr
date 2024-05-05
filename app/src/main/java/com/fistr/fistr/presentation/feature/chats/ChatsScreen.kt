package com.fistr.fistr.presentation.feature.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fistr.fistr.data.mock_data.FakeChatData
import com.fistr.fistr.presentation.feature.chat.ChatScreenNavArgs
import com.fistr.fistr.presentation.feature.chats.components.ItemChat
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ChatScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

@Destination<RootGraph>
@Composable
fun ChatsScreen(
    navigator: DestinationsNavigator,
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Content(
        navigateToChatScreen = navigator::navigate,
        uiState = uiState
    )
}

@Composable
private fun Content(
    navigateToChatScreen: (Direction) -> Unit,
    uiState: ChatsUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .then(modifier)
    ) {
        when (uiState) {
            is ChatsUiState.Loading -> LoadingState()
            is ChatsUiState.LoadFailed -> LoadFailedState(uiState = uiState)
            is ChatsUiState.Success -> SuccessState(
                navigateToChatScreen = navigateToChatScreen,
                uiState = uiState
            )
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = Modifier.then(modifier))
}

@Composable
private fun LoadFailedState(uiState: ChatsUiState.LoadFailed, modifier: Modifier = Modifier) {
    Text(
        text = uiState.errorMessage.asString(),
        modifier = Modifier.then(modifier)
    )
}

@Composable
private fun SuccessState(
    navigateToChatScreen: (Direction) -> Unit,
    uiState: ChatsUiState.Success,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .then(modifier)
    ) {
        LazyColumn {
            items(
                items = uiState.chats,
                key = {
                    it.id
                }
            ) { chat ->
                ItemChat(
                    username = chat.participants.last().fullName,
                    messagePreview = chat.preview.content,
                    modifier = Modifier.clickable {
                        navigateToChatScreen(
                            ChatScreenDestination(
                                ChatScreenNavArgs(
                                    chatID = chat.id,
                                    userID = chat.participants.last().id,
                                    fullName = chat.participants.last().fullName
                                )
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
@PreviewFontScale
private fun PreviewChatsScreen() {
    Content(
        navigateToChatScreen = {},
        uiState = ChatsUiState.Success(
            chats = FakeChatData.getAllChats()
        )
    )
}
