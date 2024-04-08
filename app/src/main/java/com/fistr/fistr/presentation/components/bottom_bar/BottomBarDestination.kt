package com.fistr.fistr.presentation.components.bottom_bar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.fistr.fistr.R
import com.ramcosta.composedestinations.generated.destinations.BrowseScreenDestination
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.generated.destinations.MessagesScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    val iconSelected: ImageVector,
    @StringRes val label: Int
) {
    Home(
        HomeScreenDestination,
        Icons.Outlined.Home,
        Icons.Filled.Home,
        R.string.home
    ),
    Browse(
        BrowseScreenDestination,
        Icons.Outlined.FavoriteBorder,
        Icons.Filled.Favorite,
        R.string.browse
    ),
    Messages(
        MessagesScreenDestination,
        Icons.Outlined.MailOutline,
        Icons.Filled.Email,
        R.string.messages
    )
}