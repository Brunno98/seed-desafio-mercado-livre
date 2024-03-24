package br.com.brunno.mercadoLivre.purchase;

import br.com.brunno.mercadoLivre.helpers.CustomMockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(CustomMockMvc.class)
public class PurchaseControllerTest {

    @Autowired
    CustomMockMvc customMockMvc;

    @Test
    @DisplayName("Should start purchase")
    void test() throws Exception {
        customMockMvc.postAuthenticated("/category", Map.of("name", "foo"));
        customMockMvc.postAuthenticated("/product", Map.of("name", "foo",
                "price", 42.79,
                "availableQuantity", 10,
                "characteristics", List.of("foo", "bar", "foobar"),
                "description", "some description...",
                "categoryId", 1));

        customMockMvc.postAuthenticated("/purchase", Map.of(
                "productId", 1,
                "quantity", 2,
                "paymentMethod", "PAYPAL"
        ))
                .andExpect(status().isFound());
    }
}
