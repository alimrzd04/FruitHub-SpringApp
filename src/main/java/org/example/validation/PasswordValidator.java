package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<Password,Object> {

    @Override
    public void initialize(Password password) {
       ConstraintValidator.super.initialize(password);
    }

    @Override
    public boolean isValid(Object input, ConstraintValidatorContext context) {
        if (input == null) {
            return false;
        }

        if (input instanceof String password) {
            return !password.trim().isEmpty() && password.length() >= 6;
        }

        return false;
    }
}
