package com.fistr.fistr.presentation.feature.browse

import com.fistr.fistr.data.model.User

sealed interface BrowseEvent {
    data object Refresh : BrowseEvent
    data class SwipedLeft(val user: User) : BrowseEvent
    data class SwipedRight(val user: User) : BrowseEvent
}