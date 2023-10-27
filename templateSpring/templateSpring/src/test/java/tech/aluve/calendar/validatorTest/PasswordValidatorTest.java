package tech.aluve.calendar.validatorTest;

import tech.aluve.calendar.dto.UserSignUpDto;
import tech.aluve.calendar.entity.User;
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
        User user1 = new User();
        user.setPassword("password123");
        user.setConfirmationPassword("password123");

        assertTrue(passwordValidator.matchingPassword(user));
        assertTrue(passwordValidator.Passwordsmatch("password123","password123",user1));
    }

    @Test
    public void testNotMatchingPassword() {
        UserSignUpDto user = new UserSignUpDto();
        User user1 = new User();
        user.setPassword("password123");
        user.setConfirmationPassword("differentpassword");

        assertFalse(passwordValidator.matchingPassword(user));
        assertFalse(passwordValidator.Passwordsmatch("password123","differentpassword",user1));
    }

    @Test
    public void testValidPassword() {
        UserSignUpDto user = new UserSignUpDto();
        String validPass = "Password123!";
        user.setPassword("Password123!");

        assertTrue(passwordValidator.validPassword(user));
        assertTrue(passwordValidator.passwordValid(validPass));
    }

    @Test
    public void testInvalidPassword() {
        UserSignUpDto user = new UserSignUpDto();
        String validPass = "password";
        user.setPassword("password"); // This password is too short and lacks special characters

        assertFalse(passwordValidator.validPassword(user));
        assertFalse(passwordValidator.passwordValid(validPass));
    }
}

