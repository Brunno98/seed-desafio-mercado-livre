package br.com.brunno.mercadoLivre.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private LocalDateTime createDate = LocalDateTime.now();

    @Deprecated
    public User() {}

    public User(String login, PlainPassword password) {
        this.login = login;
        this.password = password.hash();
    }

    public String getLogin() {
        return login;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}