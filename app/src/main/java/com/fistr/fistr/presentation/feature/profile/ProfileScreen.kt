package com.fistr.fistr.presentation.feature.profile

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fistr.fistr.R
import com.fistr.fistr.data.mock_data.FakeUserData
import com.fistr.fistr.data.mock_data.StanceType
import com.fistr.fistr.data.mock_data.WeightClassType
import com.fistr.fistr.data.model.MartialArt
import com.fistr.fistr.presentation.common.FistrIcons
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Destination<RootGraph>
@Composable
fun ProfileScreen(navigator: DestinationsNavigator, viewModel: ProfileViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Content(
        navigateBack = navigator::popBackStack,
        navigateToSettingsScreen = { navigator.navigate(SettingsScreenDestination) },
        uiState = uiState
    )
}

@Composable
private fun Content(
    navigateBack: () -> Unit,
    navigateToSettingsScreen: () -> Unit,
    uiState: ProfileUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .then(modifier)
    ) {
        ProfileTopBar(
            username = uiState.localUser?.username ?: "",
            fullName = uiState.localUser?.fullName ?: "",
            navigateBack = navigateBack,
            navigateToSettingsScreen = navigateToSettingsScreen
        )
        when (uiState.state) {
            is DataState.Loading -> LoadingState()
            is DataState.LoadFailed -> LoadFailedState(uiState = uiState.state)
            is DataState.Success -> SuccessState(uiState = uiState.state)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTopBar(
    username: String,
    fullName: String,
    navigateBack: () -> Unit,
    navigateToSettingsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .shadow(elevation = 0.25.dp)
            .then(modifier)
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.profile))
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
            actions = {
                Icon(
                    imageVector = FistrIcons.settings,
                    contentDescription = stringResource(id = R.string.settings),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(CircleShape)
                        .clickable { navigateToSettingsScreen() }
                )
            },
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                imageVector = FistrIcons.person,
                contentDescription = stringResource(R.string.profile_photo),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceContainer),
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        shape = CircleShape
                    )
                    .padding(5.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = fullName,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "@${username}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        CircularProgressIndicator(modifier = Modifier.then(modifier))
    }
}

@Composable
private fun LoadFailedState(uiState: DataState.LoadFailed, modifier: Modifier = Modifier) {
    Text(
        text = uiState.message.asString(),
        modifier = Modifier.then(modifier)
    )
}

@Composable
private fun SuccessState(uiState: DataState.Success, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .verticalScroll(state = rememberScrollState())
            .then(modifier)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Overview(
            height = uiState.user.height,
            weight = uiState.user.weight,
            age = uiState.user.age,
            weightClass = uiState.user.weightClass,
            stance = uiState.user.stance
        )
        Spacer(modifier = Modifier.height(20.dp))
        Statistics(wins = uiState.user.wins, losses = uiState.user.losses)
        Spacer(modifier = Modifier.height(20.dp))
        About(fullName = uiState.user.fullName)
        Spacer(modifier = Modifier.height(20.dp))
        Expertises(expertises = uiState.user.expertises)
        Spacer(modifier = Modifier.height(20.dp))
        Gallery()
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun Overview(
    height: Int,
    weight: Int,
    weightClass: WeightClassType,
    age: Int,
    stance: StanceType,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .then(modifier)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.height),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stringResource(R.string.height_cm, height),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.weight),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stringResource(R.string.weight_kg, weight),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Age", style = MaterialTheme.typography.titleSmall)
            Text(text = "$age", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.weight_class),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = weightClass.name.lowercase().capitalize(Locale.current),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(R.string.stance),
                style = MaterialTheme.typography.titleSmall
            )
            Text(text = stance.desc, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun Statistics(wins: Int, losses: Int, modifier: Modifier = Modifier) {
    val winSweepAngle = remember {
        val total = wins + losses
        val angle = (360 * wins) / total
        angle.toFloat()
    }
    val lossSweepAngle = remember { 360f - winSweepAngle }

    val animatedWinSweepAngle = remember { Animatable(0f) }
    val animatedLossSweepAngle = remember { Animatable(0f) }
    LaunchedEffect(key1 = true) {
        delay(500)
        animatedWinSweepAngle.animateTo(
            targetValue = winSweepAngle,
            animationSpec = tween(durationMillis = (winSweepAngle * 5).toInt())
        )

        animatedLossSweepAngle.animateTo(
            targetValue = lossSweepAngle,
            animationSpec = tween(durationMillis = (lossSweepAngle * 5).toInt())
        )
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Text(
            text = stringResource(R.string.statistics),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.wins).uppercase(),
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                )
                Spacer(
                    modifier = Modifier
                        .width(20.dp)
                        .height(3.dp)
                        .background(Color.Green)
                )
                Text(text = "$wins", style = MaterialTheme.typography.titleSmall)
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box(contentAlignment = Alignment.Center) {
                Canvas(
                    modifier = Modifier.size(64.dp),
                    onDraw = {
                        drawArc(
                            color = Color.Red,
                            startAngle = winSweepAngle,
                            sweepAngle = animatedLossSweepAngle.value,
                            useCenter = false,
                            style = Stroke(width = 20f)
                        )
                    }
                )
                Text(
                    text = "${wins + losses}",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black)
                )
                Canvas(
                    modifier = Modifier.size(64.dp),
                    onDraw = {
                        drawArc(
                            color = Color.Green,
                            startAngle = 0f,
                            sweepAngle = animatedWinSweepAngle.value,
                            useCenter = false,
                            style = Stroke(width = 20f)
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.losses).uppercase(),
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                )
                Spacer(
                    modifier = Modifier
                        .width(20.dp)
                        .height(3.dp)
                        .background(Color.Red)
                        .padding(vertical = 10.dp)
                )
                Text(text = "$losses", style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Composable
private fun About(fullName: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Text(text = stringResource(R.string.about), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "I'm $fullName, a title-winning boxer driven by passion and purpose. From breaking records to inspiring others, I'm all about pushing boundaries and making a global impact. Follow my journey and upcoming fights on [social media handles]. Let's make every round count!",
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Expertises(expertises: List<MartialArt>, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Text(
            text = stringResource(R.string.expertises),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            expertises.forEach { martialArt ->
                SuggestionChip(
                    onClick = { },
                    label = { Text(martialArt.name) },
                    colors = SuggestionChipDefaults.suggestionChipColors().copy(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    modifier = Modifier.padding(end = 5.dp)
                )
            }
        }
    }
}

@Composable
private fun Gallery(modifier: Modifier = Modifier) {
    val colors = listOf(Color.Gray, Color.DarkGray, Color.Red, Color.Blue, Color.Green)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier)
    ) {
        Text(
            text = stringResource(R.string.gallery),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            items(items = (0 until 5).toList()) {
                Image(
                    imageVector = FistrIcons.person,
                    contentDescription = it.toString(),
                    modifier = Modifier
                        .size(200.dp)
                        .background(colors[it])
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewProfileScreen() {
    Content(
        navigateToSettingsScreen = {},
        navigateBack = {},
        uiState = ProfileUiState(
            localUser = FakeUserData.getUserByID(3),
            state = DataState.Success(FakeUserData.getUserByID(3))
        )
    )
}
