package br.com.brunno.mercadoLivre.shared.validator;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class IdExistsValidator implements ConstraintValidator<IdExists, Long> {

    private final EntityManager entityManager;

    @Autowired
    public IdExistsValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Class<?> klass;

    @Override
    public void initialize(IdExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.klass = constraintAnnotation.domain();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (Objects.isNull(id)) return true;

        Object o = entityManager.find(klass, id);

        return Objects.nonNull(o);
    }
}
