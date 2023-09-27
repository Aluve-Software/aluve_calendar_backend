package tech.aluve.calendar.validatorTest;

import tech.aluve.calendar.dto.UserSignUpDto;
import tech.aluve.calendar.validator.PasswordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordValidatorTest {
    private PasswordValidator passwordValidator;

    @BeforeEach
    public void setUp() {
        passwordValidator = new PasswordValidator();
    }

    @Test
    public void testMatchingPassword() {
        UserSignUpDto user = new UserSignUpDto();
        user.setPassword("password123");
        user.setConfirmationPassword("password123");

        assertTrue(passwordValidator.matchingPassword(user));
    }

    @Test
    public void testNotMatchingPassword() {
        UserSignUpDto user = new UserSignUpDto();
        user.setPassword("password123");
        user.setConfirmationPassword("differentpassword");

        assertFalse(passwordValidator.matchingPassword(user));
    }

    @Test
    public void testValidPassword() {
        UserSignUpDto user = new UserSignUpDto();
        user.setPassword("Password123!");

        assertTrue(passwordValidator.validPassword(user));
    }

    @Test
    public void testInvalidPassword() {
        UserSignUpDto user = new UserSignUpDto();
        user.setPassword("password"); // This password is too short and lacks special characters

        assertFalse(passwordValidator.validPassword(user));
    }
}

