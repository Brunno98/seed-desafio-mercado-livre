package br.com.brunno.mercadoLivre.payment;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
    Contagem carga intrinseca:
    - PaymentListener
    - UniquePaymentTransactionIdValidator
    - PaymentCallbackRequest
    - Payment
    - Payment::isSuccess

    total: 5
 */

@RestController
public class CallbackController {

    private final EntityManager entityManager;
    private final PaymentObservable paymentObservable;
    private final UniquePaymentTransactionIdValidator uniquePaymentTransactionIdValidator;

    @Autowired
    public CallbackController(EntityManager entityManager, PaymentObservable paymentObservable, UniquePaymentTransactionIdValidator uniquePaymentTransactionIdValidator) {
        this.entityManager = entityManager;
        this.paymentObservable = paymentObservable;
        this.uniquePaymentTransactionIdValidator = uniquePaymentTransactionIdValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(uniquePaymentTransactionIdValidator);
    }

    @Transactional
    @PostMapping("/payment/callback")
    public String paymentCallback(@RequestBody @Valid PaymentCallbackRequest paymentCallbackRequest) throws BindException {
        List<Payment> transactions = entityManager.createQuery("SELECT p FROM Payment p WHERE p.purchase.id = :pId", Payment.class)
                .setParameter("pId", paymentCallbackRequest.getPurchaseId())
                .getResultList();

        boolean hasSuccess = transactions.stream().anyMatch(Payment::isSuccess);
        Assert.isTrue(!hasSuccess, "purchase already processed with success");

        Payment payment = paymentCallbackRequest.toPayment(entityManager);

        entityManager.persist(payment);

        paymentObservable.notify(payment);

        return payment.toString();
    }

}
