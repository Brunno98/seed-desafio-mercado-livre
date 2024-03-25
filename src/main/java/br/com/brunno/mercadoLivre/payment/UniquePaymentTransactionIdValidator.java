package br.com.brunno.mercadoLivre.payment;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class UniquePaymentTransactionIdValidator implements Validator {

    private final EntityManager entityManager;

    @Autowired
    public UniquePaymentTransactionIdValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PaymentCallbackRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        PaymentCallbackRequest request = (PaymentCallbackRequest) target;

        List<Payment> resultList = entityManager.createQuery("SELECT p FROM Payment p WHERE p.transactionId = :tId", Payment.class)
                .setParameter("tId", request.getTransactionId())
                .getResultList();

        if (!resultList.isEmpty()) {
            errors.rejectValue("transactionId", null, "already exists");
        }
    }
}
