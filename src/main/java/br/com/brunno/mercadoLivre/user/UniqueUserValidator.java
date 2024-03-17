package br.com.brunno.mercadoLivre.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class UniqueUserValidator implements ConstraintValidator<UniqueUser, String> {

    private final UserRepository userRepository;

    @Autowired
    public UniqueUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        if (Objects.isNull(login)) return true;

        return userRepository.findByLoginIgnoreCase(login).isEmpty();
    }
}
