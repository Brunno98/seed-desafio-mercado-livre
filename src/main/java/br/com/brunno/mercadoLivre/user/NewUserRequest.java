package br.com.brunno.mercadoLivre.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
public class NewUserRequest {

    @NotBlank
    @Email
    private String login;

    @NotBlank
    @Length(min = 6)
    private String password;

    @NotNull
    @PastOrPresent
    private LocalDateTime createDate;

    public User toDomain() {
        return new User(login, password, createDate);
    }
}
