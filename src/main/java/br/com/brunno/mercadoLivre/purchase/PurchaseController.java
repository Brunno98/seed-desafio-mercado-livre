package br.com.brunno.mercadoLivre.purchase;

import br.com.brunno.mercadoLivre.login.AuthenticationService;
import br.com.brunno.mercadoLivre.product.Product;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    Contagem de carga cognitiva
    - AuthenticationService
    - EmailSender
    - PurchaseRequest
    - User
    - Product
    - Purchase
    - if
 */

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final EntityManager entityManager;
    private final AuthenticationService authenticationService;
    private final EmailSender emailSender;

    @Autowired
    public PurchaseController(EntityManager entityManager, AuthenticationService authenticationService, EmailSender emailSender) {
        this.entityManager = entityManager;
        this.authenticationService = authenticationService;
        this.emailSender = emailSender;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> purchase(@RequestBody @Valid PurchaseRequest purchaseRequest, Authentication authentication) throws BindException {
        User user = authenticationService.getUserFromAuthentication(authentication);

        Product product = purchaseRequest.getProduct(entityManager);

        final int desiredQuantity = purchaseRequest.getQuantity();
        if (!product.hasStock(desiredQuantity)) {
            BindException bindException = new BindException(purchaseRequest, "purchaseRequest");
            bindException.rejectValue("quantity", null, "does not have stock to quantity " + desiredQuantity);
            throw bindException;
        }

        product.downStock(desiredQuantity);

        Purchase purchase = new Purchase(desiredQuantity, product, user, purchaseRequest.getPaymentMethod());
        entityManager.persist(purchase);

        String productOwnerLogin = purchase.getProduct().getOwnerLogin();
        emailSender.sendEmail("Caro vendedor " + productOwnerLogin + ", o usuario " + user.getLogin() + " está " +
                "interessado em comprar seu produto " + purchase.getProduct().getName() +
                " na quantidade de " + purchase.getQuantity());

        String redirectUri = purchase.getRedirectUri("http://localhost:8080/payment/callback");

        return ResponseEntity.status(HttpStatus.FOUND).body(redirectUri);
    }
}
