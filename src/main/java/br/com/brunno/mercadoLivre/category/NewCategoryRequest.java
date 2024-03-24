package br.com.brunno.mercadoLivre.category;

import br.com.brunno.mercadoLivre.shared.validator.IdExists;
import br.com.brunno.mercadoLivre.shared.validator.Unique;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;

/*
    Contagem de complexidade cognitiva
    (Classe com estado - limite 9)

    - Unique
    - IdExists
    - Category

    total: 3
 */
/*
    Branches para testar: 1
 */

@Getter
public class NewCategoryRequest {

    @NotBlank
    @Unique(domainClass = Category.class, fieldName = "name")
    private String name;

    @Nullable
    @IdExists(domain = Category.class)
    private Long parentCategoryId;

    public Category toDomain(EntityManager entityManager) {
        Category category = new Category(name);

        if (Objects.nonNull(parentCategoryId)) {
            Category parentCategory = entityManager.find(Category.class, parentCategoryId);
            Assert.notNull(parentCategory, "parent category must exists");
            category.setParent(parentCategory);
        }

        return category;
    }
}
