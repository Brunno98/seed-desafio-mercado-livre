package br.com.brunno.mercadoLivre.category;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/*
    Contagem de complexidade cognitiva
    (Classe sem estado - limite 7)

    - UniqueCategory
    - CategoryRepository

    total: 2
 */
/*
    Branches para testar: 1
 */
public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, String> {

    private final CategoryRepository categoryRepository;

    @Autowired
    public UniqueCategoryValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(name)) return true;

        return categoryRepository.findByNameIgnoreCase(name).isEmpty();
    }
}
