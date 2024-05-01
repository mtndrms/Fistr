package com.fistr.fistr.presentation.common.bottom_bar

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.fistr.fistr.R
import com.fistr.fistr.presentation.common.FistrIcons
import com.ramcosta.composedestinations.generated.destinations.BrowseScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ChatsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    val iconSelected: ImageVector,
    @StringRes val label: Int
) {
    Home(
        HomeScreenDestination,
        FistrIcons.homeOutlined,
        FistrIcons.homeFilled,
        R.string.home
    ),
    Browse(
        BrowseScreenDestination,
        FistrIcons.favoriteOutlined,
        FistrIcons.favoriteFilled,
        R.string.browse
    ),
    Messages(
        ChatsScreenDestination,
        FistrIcons.messageOutlined,
        FistrIcons.messageFilled,
        R.string.messages
    )
}
