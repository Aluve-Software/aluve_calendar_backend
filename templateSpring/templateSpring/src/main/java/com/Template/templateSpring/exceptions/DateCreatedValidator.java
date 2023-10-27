package com.Template.templateSpring.exceptions;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.sql.Timestamp;

public class DateCreatedValidator implements ConstraintValidator<ValidDateCreated, Timestamp> {
    @Override
    public void initialize(ValidDateCreated constraintAnnotation) {
        // Initialization, if needed
    }

    @Override
    public boolean isValid(Timestamp dateCreated, ConstraintValidatorContext context) {
        if (dateCreated == null) {
            return true; // You can change this based on your requirements
        }

        // Get the current timestamp
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        // Compare the provided dateCreated with the current timestamp
        return dateCreated.before(currentTimestamp);
    }
}
