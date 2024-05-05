package com.fistr.fistr.presentation.feature.profile

import com.fistr.fistr.data.model.User
import com.fistr.fistr.presentation.common.UiText

data class ProfileUiState(
    var isUsersOwnProfile: Boolean = false,
    val state: DataState = DataState.Loading
)

sealed interface DataState {
    data object Loading : DataState
    data class LoadFailed(val message: UiText) : DataState
    data class Success(val user: User) : DataState
}
