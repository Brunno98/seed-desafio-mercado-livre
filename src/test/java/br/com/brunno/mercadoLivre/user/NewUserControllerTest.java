package br.com.brunno.mercadoLivre.user;

import br.com.brunno.mercadoLivre.helpers.CustomMockMvc;
import jakarta.validation.constraints.NotNull;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.Chars;
import net.jqwik.api.constraints.NotBlank;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
@Import(CustomMockMvc.class)
public class NewUserControllerTest {

    private static final Set<String> generatedEmails = new HashSet<>();

    @Autowired
    CustomMockMvc mockMvc;

    @Label("Should create a user")
    @Property(tries = 20)
    void createUser(
            @ForAll @StringLength(max = 64) @AlphaChars @NotBlank String email,
            @ForAll @StringLength(min = 6) @AlphaChars @NumericChars @Chars({'!', '@', '#', '$', '%', '¨', '&', '*', '(', ')', '_', '-', '+', '+'}) String password
    ) throws Exception {
        Assumptions.assumeTrue(generatedEmails.add(email.toLowerCase()));

        Map<String, Object> payload = Map.of(
                "login", email + "@example.com",
                "password", password
        );

        mockMvc.post("/user", payload)
                .andExpect(status().isOk());

        mockMvc.post("/user", payload)
                .andExpect(status().isBadRequest());
    }
}
