package br.com.brunno.mercadoLivre.product;

import br.com.brunno.mercadoLivre.login.AuthenticationService;
import br.com.brunno.mercadoLivre.shared.IdExists;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

/*
    Contagem de carga intrinseca:
    (Classe sem estado - 7 pontos)
    - NewProductRequest
    - Product
    - AuthenticationService
    - User
    - IdExists
    - StorageService

    Total: 6
 */

/*
    total de branch para se testar: 1
 */

@RestController
public class ProductController {

    private final EntityManager entityManager;
    private final AuthenticationService authenticationService;
    private StorageService storageService = new StorageService();

    @Autowired
    public ProductController(EntityManager entityManager, AuthenticationService authenticationService) {
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

    @Transactional
    @PatchMapping("/product/{productId}/image")
    public String addImage(
            @PathVariable @Valid @IdExists(domain = Product.class) Long productId,
            @RequestParam MultipartFile[] images,
            Authentication authentication) {
        User user = authenticationService.getUserFromAuthentication(authentication);
        Product product = entityManager.find(Product.class, productId);
        if (!product.belongsTo(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Product doesnt not belongs to user");

        Arrays.stream(images).forEach(image -> {
            product.addImage("/image/" + image.getOriginalFilename());
            storageService.store(image);
        });

        entityManager.merge(product);

        return product.toString();
    }

    class StorageService {

        public void store(MultipartFile file) {
            if (file.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "empty file");
            // do store things...
        }
    }
}
