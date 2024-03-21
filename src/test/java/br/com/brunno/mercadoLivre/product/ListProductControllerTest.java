package br.com.brunno.mercadoLivre.product;

import br.com.brunno.mercadoLivre.helpers.CustomMockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(CustomMockMvc.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ListProductControllerTest {

    @Autowired
    CustomMockMvc mockMvc;

    @Test
    @DisplayName("Shold list products")
    void listProducts() throws Exception {
        mockMvc.postAuthenticated("/category", Map.of("name", "foo"))
                .andExpect(status().isOk());

        mockMvc.postAuthenticated("/product", Map.of(
                "name", "foo",
                "price", 49.99,
                "availableQuantity", 100,
                "characteristics", List.of("foo", "bar", "foobar"),
                "description", "some description...",
                "categoryId", 1
        )).andExpect(status().isOk());

        mockMvc.postAuthenticated("/product", Map.of(
                "name", "bar",
                "price", 42.79,
                "availableQuantity", 200,
                "characteristics", List.of("foo", "bar", "foobar"),
                "description", "some description...",
                "categoryId", 1
        )).andExpect(status().isOk());

        mockMvc.getAuthenticated("/product")
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").isArray(),
                        jsonPath("$").isNotEmpty(),
                        jsonPath("$.[0].id").value(1),
                        jsonPath("$.[0].category").value("foo"),
                        jsonPath("$.[0].owner").value("test@email.com"),
                        jsonPath("$.[1].id").value(2),
                        jsonPath("$.[1].category").value("foo"),
                        jsonPath("$.[1].owner").value("test@email.com")
                );
    }
}
