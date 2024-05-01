package com.fistr.fistr.presentation.feature.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ItemMessage(
    content: String,
    timestamp: String,
    isLoggedInUserOwnThisMessage: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = if (isLoggedInUserOwnThisMessage) 15.dp else 1.dp,
                        topEnd = if (isLoggedInUserOwnThisMessage) 1.dp else 15.dp,
                        bottomEnd = if (isLoggedInUserOwnThisMessage) 15.dp else 1.dp,
                        bottomStart = if (isLoggedInUserOwnThisMessage) 1.dp else 15.dp
                    )
                )
                .background(MaterialTheme.colorScheme.primary)
                .padding(10.dp)
                .align(
                    if (isLoggedInUserOwnThisMessage) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.CenterStart
                    }
                )
                .then(modifier)
        ) {
            Text(text = content, color = MaterialTheme.colorScheme.onPrimary)
            Text(
                text = timestamp,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewItemMessage() {
    ItemMessage(
        content = "Message for a preview",
        timestamp = "14:32",
        isLoggedInUserOwnThisMessage = true
    )
}