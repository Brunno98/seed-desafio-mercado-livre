package br.com.brunno.mercadoLivre.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlainPasswordTest {

    @DisplayName("Password with length less than 6 should throws exception")
    @Test
    void shortPass() {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> new PlainPassword("12345"));
    }
}
