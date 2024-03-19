package br.com.brunno.mercadoLivre.login;

import br.com.brunno.mercadoLivre.user.User;
import br.com.brunno.mercadoLivre.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserFromAuthentication(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        Assert.isAssignable(Jwt.class, principal.getClass(), "Authentication object shold be a JWT");

        Jwt jwt = (Jwt) principal;
        String userEmail = jwt.getSubject();
        Assert.hasLength(userEmail, "JWT token should have the user login");

        Optional<User> optionalUser = userRepository.findByLoginIgnoreCase(userEmail);
        Assert.isTrue(optionalUser.isPresent(), "user should exists");

        return optionalUser.get();
    }
}
