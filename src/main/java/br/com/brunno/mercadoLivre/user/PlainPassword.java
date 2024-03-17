package br.com.brunno.mercadoLivre.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class PlainPassword {

    private final String password;

    public PlainPassword(@NotBlank @Length(min = 6) String password) {
        Assert.isTrue(StringUtils.hasText(password), "Password cannot be blank");
        Assert.isTrue(password.length() >= 6, "Password must be greather or equal than 6");

        this.password = password;
    }

    public String hash() {
        return new BCryptPasswordEncoder().encode(password);
    }
}
