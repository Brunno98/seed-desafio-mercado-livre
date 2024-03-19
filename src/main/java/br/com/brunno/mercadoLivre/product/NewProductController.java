package br.com.brunno.mercadoLivre.product;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
    Contagem de carga intrinseca:
    (Classe sem estado - 7 pontos)
    - NewProductRequest
    - Product

    Total: 2
 */

/*
    total de branch para se testar: 0
 */

@RestController
public class NewProductController {

    private final EntityManager entityManager;

    @Autowired
    public NewProductController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/product")
    @Transactional
    public String createProduct(@RequestBody @Valid NewProductRequest newProductRequest) {
        Product product = newProductRequest.toProduct(entityManager);

        entityManager.persist(product);

        return product.toString();
    }
}
