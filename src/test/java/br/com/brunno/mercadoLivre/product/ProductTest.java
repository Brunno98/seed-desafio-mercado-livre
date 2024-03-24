package br.com.brunno.mercadoLivre.product;

import br.com.brunno.mercadoLivre.category.Category;
import br.com.brunno.mercadoLivre.user.PlainPassword;
import br.com.brunno.mercadoLivre.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.List;

public class ProductTest {

    private final List<String> characteristics = List.of("characterisc 1", "characterisc 2", "characterisc 3");
    private final Category category = new Category("foo");
    private final User owner = new User("foo@email.com", new PlainPassword("123456"));
    private final String productName = "foo";
    private final BigDecimal price = BigDecimal.valueOf(42.79);
    private final String description = "some description";

    @DisplayName("should has stock when availableQuantity is >= desired quantity")
    @ParameterizedTest
    @CsvSource({"1,1,true", "1,2,false", "2,1,true", "42,24,true", "24,42,false"})
    void hasStock(int availableQuantity, int desiredQuantity, boolean expectedResult) {
        Product product = new Product(productName, price, availableQuantity, characteristics, description, category, owner);

        boolean hasStock = product.hasStock(desiredQuantity);

        Assertions.assertThat(hasStock).isEqualTo(expectedResult);
    }

    @DisplayName("down stock should down available quantity of product")
    @Test
    void downStock() {
        Product product = new Product(productName, price, 100, characteristics, description, category, owner);

        product.downStock(10);

        Assertions.assertThat(product.getAvailableQuantity()).isEqualTo(90);
    }

    @DisplayName("should throws when down stock with quantity bigger than available quantity")
    @Test
    void invalidDown() {
        Product product = new Product(productName, price, 1, characteristics, description, category, owner);

        Assertions.assertThatIllegalArgumentException()
                        .isThrownBy(() -> product.downStock(10));
    }

    @DisplayName("should throws when down 0 from stock")
    @Test
    void downZero() {
        Product product = new Product(productName, price, 100, characteristics, description, category, owner);

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> product.downStock(0));
    }
}
