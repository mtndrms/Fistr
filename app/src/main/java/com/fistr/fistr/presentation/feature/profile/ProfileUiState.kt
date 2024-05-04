package com.fistr.fistr.presentation.feature.profile

import com.fistr.fistr.data.model.User
import com.fistr.fistr.presentation.common.UiText

data class ProfileUiState(
    val localUser: User? = null,
    val state: DataState = DataState.Loading
)

sealed interface DataState {
    data object Loading : DataState
    data class LoadFailed(val message: UiText) : DataState
    data class Success(val user: User) : DataState
}
