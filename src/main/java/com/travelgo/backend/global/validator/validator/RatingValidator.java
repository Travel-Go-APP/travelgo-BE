package com.travelgo.backend.global.validator.validator;

import com.travelgo.backend.global.validator.customValid.RatingValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<RatingValid, Long> {

    private float min;
    private float max;
    private String message;

    @Override
    public void initialize(RatingValid constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        boolean isValid = value >= min && value <= max;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
