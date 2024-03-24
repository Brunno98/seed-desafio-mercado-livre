package br.com.brunno.mercadoLivre.shared.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = IdExistsValidator.class)
public @interface IdExists {

    String message() default "not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class <?> domain();

}
