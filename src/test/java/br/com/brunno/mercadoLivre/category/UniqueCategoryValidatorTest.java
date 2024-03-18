package br.com.brunno.mercadoLivre.category;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class UniqueCategoryValidatorTest {

    CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);

    UniqueCategoryValidator validator = new UniqueCategoryValidator(categoryRepository);

    @Test
    @DisplayName("When search by name not found result then is valid")
    void valid() {
        Mockito.doReturn(Optional.empty()).when(categoryRepository).findByNameIgnoreCase("foo");

        boolean result = validator.isValid("foo", null);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("When find some category then is invalid")
    void invalid() {
        Mockito.doReturn(Optional.of(new Category("foo"))).when(categoryRepository).findByNameIgnoreCase("foo");

        boolean result = validator.isValid("foo", null);

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should not do validation when text is null or empty")
    void emptyText() {
        boolean emptyString = validator.isValid("", null);
        Assertions.assertThat(emptyString).isTrue();

        boolean nullValue = validator.isValid(null, null);
        Assertions.assertThat(nullValue).isTrue();
    }
}
