package br.com.brunno.mercadoLivre.user;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class NewUserController {

    private final EntityManager entityManager;

    @Autowired
    public NewUserController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @PostMapping
    public NewUserResponse createUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        User user = newUserRequest.toDomain();

        entityManager.persist(user);

        return new NewUserResponse(user);
    }
}
