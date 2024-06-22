package com.travelgo.backend.global.validator.validator;

import com.travelgo.backend.global.validator.customValid.DescriptionValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DescriptionValidator implements ConstraintValidator<DescriptionValid, String> {

    private int maxSize;
    private String message;

    @Override
    public void initialize(DescriptionValid constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String description, ConstraintValidatorContext context) {
        if (description == null)
            return false;
        boolean isValid = description.length() <= maxSize;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
