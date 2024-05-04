package com.fistr.fistr.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.fistr.fistr.R

object FistrIcons {
    // Image vectors
    val homeOutlined: ImageVector = Icons.Outlined.Home
    val homeFilled: ImageVector = Icons.Rounded.Home
    val favoriteOutlined: ImageVector = Icons.Outlined.FavoriteBorder
    val favoriteFilled: ImageVector = Icons.Rounded.Favorite
    val messageOutlined: ImageVector = Icons.Outlined.MailOutline
    val messageFilled: ImageVector = Icons.Rounded.Email
    val clear: ImageVector = Icons.Rounded.Clear
    val email: ImageVector = Icons.Rounded.Email
    val person: ImageVector = Icons.Rounded.Person
    val more: ImageVector = Icons.Rounded.MoreVert
    val send: ImageVector = Icons.AutoMirrored.Rounded.Send
    val back: ImageVector = Icons.AutoMirrored.Rounded.ArrowBack
    val settings: ImageVector = Icons.Rounded.Settings

    // Drawables
    val username: Int = R.drawable.ic_tag
    val password: Int = R.drawable.ic_password
    val camera: Int = R.drawable.ic_camera
    val attachment: Int = R.drawable.ic_attachment
    val microphone: Int = R.drawable.ic_mic
    val emoji: Int = R.drawable.ic_emoji
    val keyboard: Int = R.drawable.ic_keyboard
    val logout: Int = R.drawable.ic_logout
}
