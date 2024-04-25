package com.fistr.fistr.domain

import com.fistr.fistr.R
import com.fistr.fistr.presentation.common.UiText

sealed interface DataError : Error {
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL
    }
}

fun DataError.asText(): UiText {
    return when (this) {
        DataError.Remote.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.the_request_timed_out
        )

        DataError.Remote.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.youve_hit_your_rate_limit
        )

        DataError.Remote.NO_INTERNET -> UiText.StringResource(
            R.string.no_internet
        )

        DataError.Remote.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.file_too_large
        )

        DataError.Remote.SERVER_ERROR -> UiText.StringResource(
            R.string.server_error
        )

        DataError.Remote.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )

        DataError.Remote.UNKNOWN -> UiText.StringResource(
            R.string.unknown_error
        )

        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )
    }
}
