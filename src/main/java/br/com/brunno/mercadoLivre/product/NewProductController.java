package br.com.brunno.mercadoLivre.product;

import br.com.brunno.mercadoLivre.login.AuthenticationService;
import br.com.brunno.mercadoLivre.user.User;
import br.com.brunno.mercadoLivre.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    private final AuthenticationService authenticationService;

    @Autowired
    public NewProductController(EntityManager entityManager, AuthenticationService authenticationService) {
        this.entityManager = entityManager;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/product")
    @Transactional
    public String createProduct(@RequestBody @Valid NewProductRequest newProductRequest, Authentication authentication) {
        User owner = authenticationService.getUserFromAuthentication(authentication);

        Product product = newProductRequest.toProduct(entityManager, owner);

        entityManager.persist(product);

        return product.toString();
    }
}
