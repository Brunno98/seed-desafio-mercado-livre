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
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
@Import(CustomMockMvc.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductControllerTest {

    @Autowired
    private CustomMockMvc customMockMvc;

    @Autowired
    private MockMvc mockMvc;

    @Label("Should create product")
    @Property(tries = 20, edgeCases = EdgeCasesMode.FIRST)
    void createProduct(
            @ForAll @StringLength(max = 255) @NotBlank @AlphaChars @Whitespace String name,
            @ForAll @BigRange(max = "9999") @Scale(2) @Positive BigDecimal price,
            @ForAll @IntRange(max = 9999) @Positive int quantity,
            @ForAll @Size(min = 3, max = 30) List<@StringLength(max = 255) @NotBlank @AlphaChars @Whitespace String> characteristc,
            @ForAll @StringLength(max = 1000) @NotBlank @AlphaChars @Whitespace String description
            ) throws Exception {
        customMockMvc.postAuthenticated("/category", Map.of("name", "foo"));

        Map<String, Object> payload = Map.of(
                "name", name,
                "price", price,
                "quantityAvailable", quantity,
                "characteristics", characteristc,
                "description", description,
                "categoryId", 1
        );

        customMockMvc.postAuthenticated("/product", payload)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("User should be able to add images to your product")
    void addImagesToProduct() throws Exception {
        customMockMvc.postAuthenticated("/category", Map.of("name", "category")).andExpectAll(status().isOk());
        customMockMvc.postAuthenticated("/product", Map.of(
                "name", "foo",
                "price", 49.99,
                "quantityAvailable", 100,
                "characteristics", List.of("foo", "bar", "foobar"),
                "description", "some description...",
                "categoryId", 1
        )).andExpect(status().isOk());
        String jwt = customMockMvc.authenticate();
        MockMultipartFile file = new MockMultipartFile("images", "image.png", "image/png", "think it is image...".getBytes());
        mockMvc.perform(multipart(HttpMethod.PATCH, "/product/1/image").file((file))
                        .header("Authorization", "Bearer "+jwt))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when user add images to product that he is not owner then should return unauthorized")
    void addImageWhenIsNotProductOwner() throws Exception {
        customMockMvc.postAuthenticated("/category", Map.of("name", "category")).andExpectAll(status().isOk());
        customMockMvc.postAuthenticated("/product", Map.of(
                "name", "foo",
                "price", 49.99,
                "quantityAvailable", 100,
                "characteristics", List.of("foo", "bar", "foobar"),
                "description", "some description...",
                "categoryId", 1
        )).andExpect(status().isOk());

        customMockMvc.post("/user", Map.of("login", "other@email.com", "password", "654321"))
                .andExpect(status().isOk());
        String jwt = customMockMvc.post("/login", Map.of("username", "other@email.com", "password", "654321"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        MockMultipartFile file = new MockMultipartFile("images", "image.png", "image/png", "think it is image...".getBytes());
        mockMvc.perform(multipart(HttpMethod.PATCH, "/product/1/image").file(file)
                        .header("Authorization", "Bearer "+jwt))
                .andExpect(status().isForbidden());
    }
}
