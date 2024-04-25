package com.fistr.fistr.domain

import com.fistr.fistr.R
import com.fistr.fistr.presentation.common.UiText

sealed interface UserDataValidationError : Error {
    enum class EmailError : UserDataValidationError {
        CANNOT_BE_EMPTY,
        NOT_A_VALID_ADDRESS
    }

    enum class UsernameError : UserDataValidationError {
        CANNOT_BE_EMPTY,
        TOO_MUCH_CHARACTERS,
        HAS_SYMBOL,
    }

    enum class PasswordError : UserDataValidationError {
        CANNOT_BE_EMPTY,
        TOO_SHORT,
        TOO_MUCH_CHARACTERS,
        NO_UPPERCASE,
        NO_LOWERCASE,
        NO_DIGIT,
        NO_SYMBOL,
    }
}

fun UserDataValidationError.asText(): UiText {
    return when (this) {
        UserDataValidationError.EmailError.CANNOT_BE_EMPTY -> UiText.StringResource(
            R.string.this_field_cannot_be_empty
        )

        UserDataValidationError.EmailError.NOT_A_VALID_ADDRESS -> UiText.StringResource(
            R.string.please_provide_a_valid_email_address
        )

        UserDataValidationError.UsernameError.CANNOT_BE_EMPTY -> UiText.StringResource(
            R.string.this_field_cannot_be_empty
        )

        UserDataValidationError.UsernameError.TOO_MUCH_CHARACTERS -> UiText.StringResource(
            R.string.your_username_must_be_at_most_sixteen_two_characters
        )

        UserDataValidationError.UsernameError.HAS_SYMBOL -> UiText.StringResource(
            R.string.your_username_cannot_contain_symbols
        )

        UserDataValidationError.PasswordError.CANNOT_BE_EMPTY -> UiText.StringResource(
            R.string.this_field_cannot_be_empty
        )

        UserDataValidationError.PasswordError.TOO_SHORT -> UiText.StringResource(
            R.string.your_password_must_be_at_least_eight_characters
        )

        UserDataValidationError.PasswordError.TOO_MUCH_CHARACTERS -> UiText.StringResource(
            R.string.your_password_must_be_at_most_thirty_two_characters
        )

        UserDataValidationError.PasswordError.NO_UPPERCASE -> UiText.StringResource(
            R.string.your_password_must_contain_at_least_one_uppercase_letter
        )

        UserDataValidationError.PasswordError.NO_LOWERCASE -> UiText.StringResource(
            R.string.your_password_must_contain_at_least_one_lowercase_letter
        )

        UserDataValidationError.PasswordError.NO_DIGIT -> UiText.StringResource(
            R.string.your_password_must_contain_at_least_one_digit
        )

        UserDataValidationError.PasswordError.NO_SYMBOL -> UiText.StringResource(
            R.string.your_password_must_contain_at_least_one_symbol
        )
    }
}