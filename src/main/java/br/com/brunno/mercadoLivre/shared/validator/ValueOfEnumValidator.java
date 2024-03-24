package br.com.brunno.mercadoLivre.shared.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {

    private List<String> acceptableValues;

    @Override
    public void initialize(ValueOfEnum constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        acceptableValues = Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) return true;

        boolean valid = acceptableValues.contains(value.toString());

        if (!valid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "must be any of ["+String.join("|", acceptableValues)+"]"
            ).addConstraintViolation();
        }

        return valid;
    }

}
