package br.com.brunno.mercadoLivre.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class NewUserRequest {

    @NotBlank
    @Email
    private String login;

    @NotBlank
    @Length(min = 6)
    private String password;

    public User toDomain() {
        return new User(login, new PlainPassword(password));
    }
}
