package br.com.brunno.mercadoLivre.review;

import br.com.brunno.mercadoLivre.helpers.CustomMockMvc;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.NotBlank;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.api.constraints.Whitespace;
import net.jqwik.spring.JqwikSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
@Import(CustomMockMvc.class)
public class ReviewControllerTest {

    @Autowired
    CustomMockMvc mockMvc;

    @Property(tries = 50)
    @Label("Should create review")
    void createReview(
            @ForAll @StringLength(max = 255) @NotBlank @AlphaChars @Whitespace String title,
            @ForAll @StringLength(max = 500) @NotBlank @AlphaChars @Whitespace String description,
            @ForAll @IntRange(min = 1, max = 5) int rating
    ) throws Exception {
        mockMvc.postAuthenticated("/category", Map.of("name", "foo"));
        mockMvc.postAuthenticated("/product", Map.of(
                "name", "foo",
                "price", 49.99,
                "availableQuantity", 100,
                "characteristics", List.of("foo", "bar", "foobar"),
                "description", "some description...",
                "categoryId", 1
        ));
        mockMvc.postAuthenticated("/review/product/1", Map.of(
                "rating", rating,
                "title", title,
                "description", description
        )).andExpect(status().isOk());
    }

}
