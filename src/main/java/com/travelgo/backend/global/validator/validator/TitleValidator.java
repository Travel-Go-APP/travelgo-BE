package com.travelgo.backend.global.validator.validator;

import com.travelgo.backend.global.validator.customValid.TitleValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TitleValidator implements ConstraintValidator<TitleValid, String> {

    private int min;
    private int max;
    private String message;

    @Override
    public void initialize(TitleValid constraintAnnotation) {
        this.min = constraintAnnotation.minSize();
        this.max = constraintAnnotation.maxSize();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        if (title == null)
            return false;
        boolean isValid = title.length() >= min && title.length() <= max;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}