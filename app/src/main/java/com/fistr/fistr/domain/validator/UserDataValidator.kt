package com.fistr.fistr.domain.validator

import com.fistr.fistr.domain.Result
import com.fistr.fistr.domain.UserDataValidationError

class UserDataValidator {
    private object PasswordRule {
        const val MIN_PASSWORD_LENGTH = 8
        const val MAX_PASSWORD_LENGTH = 32
    }

    private object UsernameRule {
        const val MAX_USERNAME_LENGTH = 24
    }

    private fun containsSymbols(input: String): Boolean {
        val pattern = Regex("[!@#\$%^&*()_+{}\\[\\]:;<>,.?/\\\\-]")
        return pattern.containsMatchIn(input)
    }

    /**
     * Checks if the given email is follows the required rules.
     *
     * @param email Email to be validated
     * @return [Result] Unit if there is no error, [UserDataValidationError.EmailError] if there is one
     */
    fun validateEmail(email: String): Result<Unit, UserDataValidationError.EmailError> {
        if (email.isEmpty()) {
            return Result.Error(UserDataValidationError.EmailError.CANNOT_BE_EMPTY)
        }

        if (!email.contains("@")) {
            return Result.Error(UserDataValidationError.EmailError.NOT_A_VALID_ADDRESS)
        }

        return Result.Success(Unit)
    }

    /**
     * Checks if the given username is follows the required rules.
     *
     * @param username Username to be validated
     * @return [Result] Unit if there is no error, [UserDataValidationError.UsernameError] if there is one
     */
    fun validateUsername(username: String): Result<Unit, UserDataValidationError.UsernameError> {
        if (username.isEmpty()) {
            return Result.Error(UserDataValidationError.UsernameError.CANNOT_BE_EMPTY)
        }

        if (username.length > UsernameRule.MAX_USERNAME_LENGTH) {
            return Result.Error(UserDataValidationError.UsernameError.TOO_MUCH_CHARACTERS)
        }

        val hasSymbol = containsSymbols(username)
        if (hasSymbol) {
            return Result.Error(UserDataValidationError.UsernameError.HAS_SYMBOL)
        }

        return Result.Success(Unit)
    }

    /**
     * Checks if the given password is follows the required rules.
     * Password length needs to be between 8 to 32 characters.
     * A password needs to contain atleast one;
     * 1. Digit
     * 2. Uppercase letter
     * 3. Lowercase letter
     * 4. Symbol
     *
     * @param password Password to be validated
     * @return [Result] Unit if there is no error, [UserDataValidationError.PasswordError] if there is one
     */
    fun validatePassword(password: String): Result<Unit, UserDataValidationError.PasswordError> {
        if (password.isEmpty()) {
            return Result.Error(UserDataValidationError.PasswordError.CANNOT_BE_EMPTY)
        }

        if (password.length < PasswordRule.MIN_PASSWORD_LENGTH) {
            return Result.Error(UserDataValidationError.PasswordError.TOO_SHORT)
        }

        if (password.length > PasswordRule.MAX_PASSWORD_LENGTH) {
            return Result.Error(UserDataValidationError.PasswordError.TOO_MUCH_CHARACTERS)
        }

        val hasDigit = password.any { char -> char.isDigit() }
        if (!hasDigit) {
            return Result.Error(UserDataValidationError.PasswordError.NO_DIGIT)
        }

        val hasUpperCase = password.any { char -> char.isUpperCase() }
        if (!hasUpperCase) {
            return Result.Error(UserDataValidationError.PasswordError.NO_UPPERCASE)
        }

        val hasLowerCase = password.any { char -> char.isLowerCase() }
        if (!hasLowerCase) {
            return Result.Error(UserDataValidationError.PasswordError.NO_LOWERCASE)
        }

        val hasSymbol = containsSymbols(password)
        if (!hasSymbol) {
            return Result.Error(UserDataValidationError.PasswordError.NO_SYMBOL)
        }

        return Result.Success(Unit)
    }
}
