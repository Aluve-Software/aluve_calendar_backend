package tech.aluve.calendar.validatorTest;

import tech.aluve.calendar.dto.UserSignUpDto;
import tech.aluve.calendar.validator.EmailValidator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmailValidatorTest {

    private EmailValidator emailValidator;

    @Before
    public void setUp() {
        emailValidator = new EmailValidator();
    }

    @Test
    public void testValidEmail() {
        UserSignUpDto user = new UserSignUpDto();
        user.setEmail("valid.email@example.com");
        assertTrue(emailValidator.validateEmail(user));
    }

    @Test
    public void testInvalidEmail() {
        UserSignUpDto user = new UserSignUpDto();
        user.setEmail("invalid-email");
        assertFalse(emailValidator.validateEmail(user));
    }

    @Test
    public void testNullEmail() {
        UserSignUpDto user = new UserSignUpDto();
        user.setEmail(null);
        assertTrue(emailValidator.emailNull(user));
    }

    @Test
    public void testEmptyEmail() {
        UserSignUpDto user = new UserSignUpDto();
        user.setEmail("");
        assertFalse(emailValidator.validateEmail(user));
    }

    @Test
    public void testTooLongEmail() {
        UserSignUpDto user = new UserSignUpDto();
        user.setEmail("a".repeat(51) + "@example.com");
        assertFalse(emailValidator.validateEmail(user));
    }

    @Test
    public void testMaxLengthEmail() {
        UserSignUpDto user = new UserSignUpDto();
        user.setEmail("a".repeat(38) + "@example.com");
        assertTrue(emailValidator.validateEmail(user));
    }
}
