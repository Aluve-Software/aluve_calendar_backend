package com.Template.templateSpring.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateCreatedValidator.class)
@Documented
public @interface ValidDateCreated {
    String message() default "Invalid dateCreated";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}