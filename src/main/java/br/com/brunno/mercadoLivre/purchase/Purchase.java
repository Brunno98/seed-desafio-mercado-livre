package br.com.brunno.mercadoLivre.purchase;

import br.com.brunno.mercadoLivre.product.Product;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int quantity;

    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne(optional = false)
    private User buyer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private String status;

    @Deprecated
    public Purchase() {}

    public Purchase(int quantity, Product product, User buyer, PaymentMethod paymentMethod) {
        this.quantity = quantity;
        this.product = product;
        this.buyer = buyer;
        this.paymentMethod = paymentMethod;
        this.status = "STARTED";
    }

    public Product getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getRedirectUri(String callbackUrl) {
        return this.paymentMethod.getRedirectLink(id, callbackUrl);
    }

    public User getBuyer() {
        return this.buyer;
    }

    public String getId() {
        return this.id;
    }

    public PaymentMethod getPaymenyMethod() {
        return paymentMethod;
    }
}
