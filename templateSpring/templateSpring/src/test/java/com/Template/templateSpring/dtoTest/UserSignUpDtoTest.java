package com.Template.templateSpring.dtoTest;

import com.Template.templateSpring.dto.UserSignUpDto;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserSignUpDtoTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testValidUserSignUpDto() {
        UserSignUpDto userSignUpDto = new UserSignUpDto();
        userSignUpDto.setEmail("test@example.com");
        userSignUpDto.setPassword("Password123");
        userSignUpDto.setConfirmationPassword("Password123");

        Set<ConstraintViolation<UserSignUpDto>> violations = validator.validate(userSignUpDto);
        assertTrue(violations.isEmpty(), "No validation errors should be present");
    }

    @Test
    public void testInvalidEmail() {
        UserSignUpDto userSignUpDto = new UserSignUpDto();
        userSignUpDto.setEmail(""); // Empty email should be invalid
        userSignUpDto.setPassword("Password123");
        userSignUpDto.setConfirmationPassword("Password123");

        Set<ConstraintViolation<UserSignUpDto>> violations = validator.validate(userSignUpDto);
        assertFalse(violations.isEmpty(), "Validation errors should be present for empty email");
    }

    @Test
    public void testInvalidPassword() {
        UserSignUpDto userSignUpDto = new UserSignUpDto();
        userSignUpDto.setEmail("test@example.com");
        userSignUpDto.setPassword(""); // Empty password should be invalid
        userSignUpDto.setConfirmationPassword("Password123");

        Set<ConstraintViolation<UserSignUpDto>> violations = validator.validate(userSignUpDto);
        assertFalse(violations.isEmpty(), "Validation errors should be present for empty password");
    }

    @Test
    public void testPasswordMismatch() {
        UserSignUpDto userSignUpDto = new UserSignUpDto();
        userSignUpDto.setEmail("test@example.com");
        userSignUpDto.setPassword("Password123");
        userSignUpDto.setConfirmationPassword("DifferentPassword"); // Password mismatch

        Set<ConstraintViolation<UserSignUpDto>> violations = validator.validate(userSignUpDto);
        assertFalse(violations.isEmpty(), "Validation errors should be present for password mismatch");
    }
}
