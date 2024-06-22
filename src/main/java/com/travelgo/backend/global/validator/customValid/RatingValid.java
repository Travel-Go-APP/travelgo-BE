package com.travelgo.backend.global.validator.customValid;

import com.travelgo.backend.global.validator.validator.RatingValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RatingValidator.class)
public @interface RatingValid {
    String message() default "별점은 1 ~ 5점 내에서 입력해야합니다.";

    Class[] groups() default {};

    Class[] payload() default {};

    float min() default 1;
    float max() default 5;
}
