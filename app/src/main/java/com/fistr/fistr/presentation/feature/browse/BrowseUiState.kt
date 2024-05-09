package com.fistr.fistr.presentation.feature.browse

import com.fistr.fistr.data.model.User

data class BrowseUiState(
    val users: List<User> = emptyList()
)
