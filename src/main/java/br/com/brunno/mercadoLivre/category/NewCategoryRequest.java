package br.com.brunno.mercadoLivre.category;

import br.com.brunno.mercadoLivre.shared.IdExists;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;

@Getter
public class NewCategoryRequest {

    @NotBlank
    @UniqueCategory
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
