package br.com.brunno.mercadoLivre.purchase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

import static br.com.brunno.mercadoLivre.purchase.PaymentMethod.PAGSEGURO;
import static br.com.brunno.mercadoLivre.purchase.PaymentMethod.PAYPAL;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Purchase purchase;

    private String transactionId;

    private String status;

    private LocalDateTime createDate = LocalDateTime.now();

    @Deprecated
    public Payment() {}

    public Payment(Purchase purchase, String transactionId, String purchaseStatus) {
        this.purchase = purchase;
        this.transactionId = transactionId;
        this.status = purchase.getPaymenyMethod().getPaymentStatus(purchaseStatus);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", purchase=" + purchase +
                ", transactionId='" + transactionId + '\'' +
                ", status='" + status + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    public Purchase getPurchase() {
        return this.purchase;
    }

    public boolean isSuccess() {
        return "SUCCESS".equals(this.status);
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
