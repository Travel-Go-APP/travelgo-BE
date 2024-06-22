package com.travelgo.backend.global.validator.customValid;

import com.travelgo.backend.global.validator.validator.ImageUrlValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageUrlValidator.class)
public @interface ImageUrlValid {
    String message() default "s3 이미지 URL이 올바르지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
