package com.fistr.fistr.domain.validator

import com.fistr.fistr.domain.Result
import com.fistr.fistr.domain.UserDataValidationError
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserEntityDataValidatorTest {
    private lateinit var userDataValidator: UserDataValidator

    @Before
    fun setUp() {
        userDataValidator = UserDataValidator()
    }

    @Test
    fun `Password can't have less than 8 characters`() {
        val result = userDataValidator.validatePassword("1234Ab.")
        assertEquals(
            (result as Result.Error).error,
            UserDataValidationError.PasswordError.TOO_SHORT
        )
    }

    @Test
    fun `Password can't have more than 32 characters`() {
        val result = userDataValidator.validatePassword("abcdeF1.0123456789AbCdEfGhKlMnPrS")
        assertEquals(
            (result as Result.Error).error,
            UserDataValidationError.PasswordError.TOO_MUCH_CHARACTERS
        )
    }

    @Test
    fun `Password doesn't contains any uppercase character`() {
        val result = userDataValidator.validatePassword("abcdef1.")
        assertEquals(
            (result as Result.Error).error,
            UserDataValidationError.PasswordError.NO_UPPERCASE
        )
    }

    @Test
    fun `Password doesn't contains any lowercase character`() {
        val result = userDataValidator.validatePassword("123456A.")
        assertEquals(
            (result as Result.Error).error,
            UserDataValidationError.PasswordError.NO_LOWERCASE
        )
    }

    @Test
    fun `Password must contain at least one digit`() {
        val result = userDataValidator.validatePassword("abcdefG.")
        assertEquals(
            (result as Result.Error).error,
            UserDataValidationError.PasswordError.NO_DIGIT
        )
    }

    @Test
    fun `Password doesn't contains any symbol`() {
        val result = userDataValidator.validatePassword("123456Ab")
        assertEquals(
            (result as Result.Error).error,
            UserDataValidationError.PasswordError.NO_SYMBOL
        )
    }

    @Test
    fun `Email provided not valid`() {
        val result = userDataValidator.validateEmail("testuser.com")
        assertEquals(
            (result as Result.Error).error,
            UserDataValidationError.EmailError.NOT_A_VALID_ADDRESS
        )
    }

    @Test
    fun `Username can't have more than 16 charactes`() {
        val result = userDataValidator.validateUsername("testusertestuser123456789")
        assertEquals(
            (result as Result.Error).error,
            UserDataValidationError.UsernameError.TOO_MUCH_CHARACTERS
        )
    }

    @Test
    fun `Username can't contain any symbol`() {
        val result = userDataValidator.validateUsername("testuser.")
        assertEquals(
            (result as Result.Error).error,
            UserDataValidationError.UsernameError.HAS_SYMBOL
        )
    }
}