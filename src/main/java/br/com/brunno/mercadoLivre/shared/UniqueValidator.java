package br.com.brunno.mercadoLivre.shared;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private final EntityManager entityManager;

    @Autowired
    public UniqueValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Class<?> klass;
    private String fieldName;

    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.klass = constraintAnnotation.domainClass();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) return true;

        List<?> resultList = entityManager
                .createQuery("SELECT e FROM "+klass.getName()+" e WHERE LOWER("+fieldName+") = LOWER(:value)")
                .setParameter("value", value)
                .getResultList();

        Assert.state(resultList.size() <= 1,
                "Find more than 1 result when search by "+klass.getName()+" on field "+fieldName+" that should be unique");

        return resultList.isEmpty();
    }
}
