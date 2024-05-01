package com.fistr.fistr.presentation.common

import androidx.compose.material3.SnackbarDuration

data class SnackbarOptions(
    val message: String,
    var actionLabel: String? = null,
    var duration: SnackbarDuration = SnackbarDuration.Long,
    var onActionPerformed: (() -> Unit) = {},
    var onDismissed: (() -> Unit) = {},
)
