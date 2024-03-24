package br.com.brunno.mercadoLivre.shared.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {

    String message() default "already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> domainClass();

    String fieldName();

}
