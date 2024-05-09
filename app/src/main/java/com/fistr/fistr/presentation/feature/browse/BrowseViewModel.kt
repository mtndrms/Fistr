package com.fistr.fistr.presentation.feature.browse

import androidx.lifecycle.ViewModel
import com.fistr.fistr.data.mapper.toUserList
import com.fistr.fistr.data.mock_data.FakeUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(BrowseUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(users = FakeUserData.getAllUsers().toUserList()) }
    }

    fun onEvent(event: BrowseEvent) {
        when (event) {
            is BrowseEvent.SwipedLeft -> {
                val updated = uiState.value.users.toMutableList().also {
                    it.removeLast()
                }

                _uiState.update { it.copy(users = updated) }
            }

            is BrowseEvent.SwipedRight -> {
                val updated = uiState.value.users.toMutableList().also {
                    it.removeLast()
                }

                _uiState.update { it.copy(users = updated) }
            }
        }
    }
}
