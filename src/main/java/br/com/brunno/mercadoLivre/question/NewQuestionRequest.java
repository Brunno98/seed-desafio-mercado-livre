package br.com.brunno.mercadoLivre.question;

import br.com.brunno.mercadoLivre.product.Product;
import br.com.brunno.mercadoLivre.shared.IdExists;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class NewQuestionRequest {

    @NotBlank
    private String title;

    @NotNull
    @IdExists(domain = Product.class)
    private Long productId;

    public Question toQuestion(EntityManager entityManager, User author) {
        Product product = entityManager.find(Product.class, productId);
        Assert.notNull(product, "Cannot convert a question request to question without product");
        return new Question(title, author, product);
    }

}
