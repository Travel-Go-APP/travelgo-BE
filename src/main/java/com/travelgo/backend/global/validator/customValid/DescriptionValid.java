package com.travelgo.backend.global.validator.customValid;

import com.travelgo.backend.global.validator.validator.DescriptionValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DescriptionValidator.class)
public @interface DescriptionValid {
    String message() default "최대 1000자까지 작성 가능합니다.";

    Class[] groups() default {};

    Class[] payload() default {};

    int maxSize() default 1000;
}
