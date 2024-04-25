package com.fistr.fistr.presentation.feature.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import com.fistr.fistr.presentation.feature.messages.components.ItemMessage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Destination<RootGraph>
@Composable
fun MessagesScreen() {
    Content()
}

@Composable
private fun Content(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn {
            items(
                items = (0..20).toList(),
                key = {
                    it
                }
            ) { message ->
                ItemMessage(
                    username = "User $message",
                    messagePreview = "Message preview for User $message"
                )
            }
        }
    }
}

@Composable
@PreviewFontScale
private fun PreviewMessagesScreen() {
    Content()
}