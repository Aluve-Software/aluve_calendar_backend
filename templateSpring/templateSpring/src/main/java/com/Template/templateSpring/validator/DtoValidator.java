package com.Template.templateSpring.validator;

import jakarta.validation.Validation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class DtoValidator {

        private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        private static Validator validator = factory.getValidator();

        public static <T> Set<ConstraintViolation<T>> validate(T entity){
            return validator.validate(entity);

        }

}
