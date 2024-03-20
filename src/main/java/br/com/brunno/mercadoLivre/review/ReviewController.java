package br.com.brunno.mercadoLivre.review;

import br.com.brunno.mercadoLivre.login.AuthenticationService;
import br.com.brunno.mercadoLivre.product.Product;
import br.com.brunno.mercadoLivre.shared.IdExists;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

    private final EntityManager entityManager;
    private final AuthenticationService authenticationService;

    @Autowired
    public ReviewController(EntityManager entityManager, AuthenticationService authenticationService) {
        this.entityManager = entityManager;
        this.authenticationService = authenticationService;
    }

    @Transactional
    @PostMapping("/review/product/{id}")
    public String postReview(
            @RequestBody @Valid NewReviewRequest newReviewRequest,
            @PathVariable(value = "id") @Valid @IdExists(domain = Product.class) Long productId,
            Authentication authentication
    ) {
        User author = authenticationService.getUserFromAuthentication(authentication);
        Product product = entityManager.find(Product.class, productId);
        Review review = newReviewRequest.toReview(product, author);

        entityManager.persist(review);

        return review.toString();
    }
}
