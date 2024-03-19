package br.com.brunno.mercadoLivre.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    @DisplayName("when login and password are equals then match should return true")
    void match() {
        String login = "foo";
        String password = "secret";
        User user = new User(login, new PlainPassword(password));

        boolean match = user.credentialsMatch(login, password);

        Assertions.assertThat(match).isTrue();
    }

    @Test
    @DisplayName("when login is diferent thne match should return false")
    void wrongLogin() {
        String login = "foo";
        String password = "secret";
        User user = new User(login, new PlainPassword(password));

        boolean match = user.credentialsMatch("other login", password);

        Assertions.assertThat(match).isFalse();
    }

    @Test
    @DisplayName("when password is diferent then match should return false")
    void wrongPassword() {
        String login = "foo";
        String password = "secret";
        User user = new User(login, new PlainPassword(password));

        boolean match = user.credentialsMatch(login, "other password");

        Assertions.assertThat(match).isFalse();
    }
}
