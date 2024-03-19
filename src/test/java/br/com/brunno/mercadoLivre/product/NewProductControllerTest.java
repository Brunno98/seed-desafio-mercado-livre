package br.com.brunno.mercadoLivre.product;

import br.com.brunno.mercadoLivre.helpers.CustomMockMvc;
import net.jqwik.api.EdgeCasesMode;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.NotBlank;
import net.jqwik.api.constraints.Positive;
import net.jqwik.api.constraints.Scale;
import net.jqwik.api.constraints.Size;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.api.constraints.Whitespace;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
@Import(CustomMockMvc.class)
@WithMockUser
public class NewProductControllerTest {

    @Autowired
    private CustomMockMvc mockMvc;

    @Label("Should create product")
    @Property(tries = 100, edgeCases = EdgeCasesMode.FIRST)
    void createProduct(
            @ForAll @StringLength(max = 255) @NotBlank @AlphaChars @Whitespace String name,
            @ForAll @BigRange(max = "9999") @Scale(2) @Positive BigDecimal price,
            @ForAll @IntRange(max = 9999) @Positive int quantity,
            @ForAll @Size(min = 3, max = 30) List<@StringLength(max = 255) @NotBlank @AlphaChars @Whitespace String> characteristc,
            @ForAll @StringLength(max = 1000) @NotBlank @AlphaChars @Whitespace String description
            ) throws Exception {
        mockMvc.post("/category", Map.of("name", "foo"));

        Map<String, Object> payload = Map.of(
                "name", name,
                "price", price,
                "quantityAvailable", quantity,
                "characteristics", characteristc,
                "description", description,
                "categoryId", 1
        );

        mockMvc.post("/product", payload)
                .andExpect(status().isOk());
    }
}
