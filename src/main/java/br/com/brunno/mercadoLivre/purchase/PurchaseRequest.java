package br.com.brunno.mercadoLivre.purchase;

import br.com.brunno.mercadoLivre.product.Product;
import br.com.brunno.mercadoLivre.shared.validator.IdExists;
import br.com.brunno.mercadoLivre.shared.validator.ValueOfEnum;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

import java.util.Objects;

@Getter
public class PurchaseRequest {

    @Positive
    private int quantity;

    @NotNull
    private Long productId;

    @NotNull
    @ValueOfEnum(enumClass = PaymentMethod.class)
    private String paymentMethod;

    public Product getProduct(EntityManager entityManager) throws BindException {
        Product product = entityManager.find(Product.class, productId);
        if (Objects.isNull(product)) {
            BindException bindException = new BindException(this, "purchaseRequest");
            bindException.rejectValue("productId", null, "not found");
            throw bindException;
        }
        return product;
    }

    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.valueOf(paymentMethod);
    }
}
