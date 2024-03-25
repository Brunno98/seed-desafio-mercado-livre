package br.com.brunno.mercadoLivre.payment;

import br.com.brunno.mercadoLivre.payment.Payment;
import br.com.brunno.mercadoLivre.purchase.Purchase;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

import java.util.Objects;

@Getter
public class PaymentCallbackRequest {

    @NotBlank
    private String purchaseId;

    @NotBlank
    private String transactionId;

    @NotBlank
    private String purchaseStatus;

    public Payment toPayment(EntityManager entityManager) throws BindException {
        Purchase purchase = this.getPurchase(entityManager);
        Assert.notNull(purchase, "purchaseId should find an purchase");
        return new Payment(purchase, transactionId, purchaseStatus);
    }

    private Purchase getPurchase(EntityManager entityManager) throws BindException {
        Purchase purchase = entityManager.find(Purchase.class, purchaseId);
        if (Objects.isNull(purchase)) {
            BindException bindException = new BindException(this, "paymentCallbackRequest");
            bindException.rejectValue("purchaseId", null, "not found");
            throw bindException;
        }
        return purchase;
    }
}
