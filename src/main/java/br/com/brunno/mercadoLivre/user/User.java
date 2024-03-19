package br.com.brunno.mercadoLivre.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;

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


    /*
        (a) login equals
        (b) password equals

        a b   &&
        v v = v
        f v = f
        v f = f
     */
    public boolean credentialsMatch(String login, String password) {
        if (!this.login.equalsIgnoreCase(login)) return false;
        return BCrypt.checkpw(password, this.password);
    }
}
