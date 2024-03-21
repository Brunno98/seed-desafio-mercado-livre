package br.com.brunno.mercadoLivre.product;

import br.com.brunno.mercadoLivre.category.Category;
import lombok.Getter;

import java.util.Objects;

@Getter
public class CategoryDetails {

    private String name;
    private CategoryDetails parent;

    public CategoryDetails(Category category) {
        name = category.getName();
        if (Objects.nonNull(category.getParent())) {
            parent = new CategoryDetails(category.getParent());
        }
    }
}
