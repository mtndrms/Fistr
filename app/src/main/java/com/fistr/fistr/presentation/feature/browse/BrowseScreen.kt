package com.fistr.fistr.presentation.feature.browse

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fistr.fistr.data.mapper.toUserList
import com.fistr.fistr.data.mock_data.FakeUserData
import com.fistr.fistr.data.model.User
import com.fistr.fistr.presentation.common.ExpertisesChips
import com.fistr.fistr.presentation.common.FistrIcons
import com.fistr.fistr.presentation.feature.profile.ProfileScreenNavArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

@Destination<RootGraph>
@Composable
fun BrowseScreen(navigator: DestinationsNavigator, viewModel: BrowseViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = LocalLifecycleOwner.current)

    Content(
        onEvent = viewModel::onEvent,
        navigateToProfileScreen = navigator::navigate,
        uiState = uiState
    )
}

@Composable
private fun Content(
    onEvent: (BrowseEvent) -> Unit,
    navigateToProfileScreen: (Direction) -> Unit,
    uiState: BrowseUiState,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            uiState.users.forEachIndexed { index, user ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.wrapContentHeight()
                ) {
                    UserCard(
                        onEvent = onEvent,
                        navigateToProfileScreen = navigateToProfileScreen,
                        user = user
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    Controls(onEvent = onEvent, user = user)
                }
            }
        }
    }
}

@Composable
private fun UserCard(
    onEvent: (BrowseEvent) -> Unit,
    navigateToProfileScreen: (Direction) -> Unit,
    user: User,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.65f)
            .clip(shape = RoundedCornerShape(16.dp))
    ) {
        Image(
            imageVector = FistrIcons.person,
            contentDescription = "cover",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainer)
        )

        Text(
            text = "@${user.username}",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.05f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .clickable {
                    navigateToProfileScreen(
                        ProfileScreenDestination(
                            navArgs = ProfileScreenNavArgs(
                                userID = user.id
                            )
                        )
                    )
                }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "${user.fullName.split(" ").first()}, ${user.age}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            ExpertisesChips(
                showTitle = false,
                expertises = user.expertises,
                modifier = Modifier.alpha(0.8f)
            )
        }
    }
}

@Composable
private fun Controls(onEvent: (BrowseEvent) -> Unit, user: User) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Icon(
            imageVector = FistrIcons.refresh,
            contentDescription = "refresh",
            tint = Color.Cyan,
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = CircleShape
                )
                .padding(8.dp)
                .clickable {
                    onEvent(BrowseEvent.Refresh)
                }
        )
        Spacer(modifier = Modifier.width(40.dp))
        Icon(
            imageVector = FistrIcons.cancel,
            contentDescription = "cancel",
            tint = Color.Red,
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = CircleShape
                )
                .padding(8.dp)
                .clickable {
                    onEvent(BrowseEvent.SwipedLeft(user = user))
                }
        )
        Spacer(modifier = Modifier.width(40.dp))
        Icon(
            imageVector = FistrIcons.done,
            contentDescription = "done",
            tint = Color.Green,
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = CircleShape
                )
                .padding(8.dp)
                .clickable {
                    onEvent(BrowseEvent.SwipedRight(user = user))
                }
        )
    }
}

@Preview
@Composable
private fun PreviewBrowseScreen() {
    Content(
        onEvent = {},
        navigateToProfileScreen = {},
        uiState = BrowseUiState(FakeUserData.getAllUsers().toUserList())
    )
}
