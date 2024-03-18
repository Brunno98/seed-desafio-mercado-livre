package br.com.brunno.mercadoLivre.category;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    Contagem de complexidade cognitiva
    (Classe sem estado - limite 7)

    - NewCategoryRequest
    - Category

    total: 2
 */
/*
    Branches para testar: 0
*/

@RestController
@RequestMapping("/category")
public class NewCategoryController {

    private final EntityManager entityManager;

    @Autowired
    public NewCategoryController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping
    @Transactional
    public String createCategory(@RequestBody @Valid NewCategoryRequest newCategoryRequest) {
        Category category = newCategoryRequest.toDomain(entityManager);

        entityManager.persist(category);

        return category.toString();
    }
}
