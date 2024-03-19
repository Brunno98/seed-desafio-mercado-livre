package br.com.brunno.mercadoLivre.product;

import br.com.brunno.mercadoLivre.category.Category;
import br.com.brunno.mercadoLivre.shared.IdExists;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

/*
    Contagem carga intrinseca
    (classe com estado - 9 pontos)
    - IdExists
    - Category
    - Product

    Total: 3
 */

/*
    branches para se testar: 0
 */

@Getter
public class NewProductRequest {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    @Positive
    private int quantityAvailable;

    @Size(min = 3)
    private List<@NotBlank String> characteristics;

    @NotBlank
    @Length(max = 1000)
    private String description;

    @NotNull
    @IdExists(domain = Category.class)
    private Long categoryId;

    public Product toProduct(EntityManager entityManager) {
        Category category = entityManager.find(Category.class, categoryId);
        Assert.notNull(category, "Category must exists");
        return new Product(name, price, quantityAvailable, characteristics, description, category);
    }
}
