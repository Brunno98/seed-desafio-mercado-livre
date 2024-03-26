package br.com.brunno.mercadoLivre.login;

import br.com.brunno.mercadoLivre.helpers.CustomMockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(CustomMockMvc.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoginControllerTest {

    @Autowired
    CustomMockMvc mockMvc;

    private final String login = "test@email.com";
    private final String password = "secret";

    @DisplayName("when login should return JWT")
    @Test
    void login() throws Exception {
        mockMvc.post("/user", Map.of("login", login, "password", password))
                .andExpect(status().isOk());

        mockMvc.post("/login", Map.of("username", login, "password", password))
                .andExpect(status().isOk());
    }

    @DisplayName("when user dont exists should return unauthorized")
    @Test
    void dontExists() throws Exception {
        mockMvc.post("/login", Map.of("username", login, "password", password))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("when password is wrong should return unauthorized")
    @Test
    void wrongPassword() throws Exception {
        mockMvc.post("/user", Map.of("login", login, "password", password))
                .andExpect(status().isOk());

        mockMvc.post("/login", Map.of("username", login, "password", "wrongPass"))
                .andExpect(status().isUnauthorized());
    }

}
