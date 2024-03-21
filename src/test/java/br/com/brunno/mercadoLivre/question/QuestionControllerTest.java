package br.com.brunno.mercadoLivre.question;

import br.com.brunno.mercadoLivre.helpers.CustomMockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import({CustomMockMvc.class})
public class QuestionControllerTest {

    @Autowired
    CustomMockMvc customMockMvc;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Should create question")
    void createQuestion() throws Exception {
        customMockMvc.postAuthenticated("/category", Map.of("name", "foo"));
        customMockMvc.postAuthenticated("/product", Map.of(
                "name", "foo",
                "price", 49.99,
                "quantityAvailable", 100,
                "characteristics", List.of("foo", "bar", "foobar"),
                "description", "some description...",
                "categoryId", 1
        ));

        customMockMvc.post("/user", Map.of("login", "other@email.com", "password", "123456"));
        String jwt =
                customMockMvc.post("/login", Map.of("username", "other@email.com", "password", "123456"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.post("/question")
                .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+jwt)
                .content(new ObjectMapper().writeValueAsString(Map.of("title", "foo?", "productId", 1))))
                .andExpect(status().isOk());
    }

}
