package br.com.brunno.mercadoLivre.category;

import br.com.brunno.mercadoLivre.helpers.CustomMockMvc;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.NotBlank;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
@Import(CustomMockMvc.class)
public class NewCategoryControllerTest {

    private static final Set<String> generatedNames = new HashSet<>();

    @Autowired
    CustomMockMvc mockMvc;

    @Label("should create category")
    @Property(tries = 20)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void createCategory(
            @ForAll @StringLength(max = 255) @AlphaChars @NotBlank String name
    ) throws Exception {
        Assumptions.assumeTrue(generatedNames.add(name.toLowerCase()));

        mockMvc.post("/category", Map.of("name", name))
                .andExpect(status().isOk());

        mockMvc.post("/category", Map.of("name", name))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("create a category with a parent that does not exists should return bad request")
    void parentNotFound() throws Exception {
        mockMvc.post("/category", Map.of(
                        "name", "some category",
                        "parentCategoryId", 99
                    ))
                .andExpect(status().isBadRequest());
    }
}
