package com.travelgo.backend.global.validator.customValid;

import com.travelgo.backend.global.validator.validator.TitleValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TitleValidator.class)
public @interface TitleValid {
    String message() default "제목은 2 ~ 15자 까지 입력가능합니다";

    Class[] groups() default {};

    Class[] payload() default {};

    int minSize() default 2;
    int maxSize() default 15;
}
