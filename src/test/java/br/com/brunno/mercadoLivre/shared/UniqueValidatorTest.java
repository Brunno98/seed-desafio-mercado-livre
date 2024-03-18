package br.com.brunno.mercadoLivre.shared;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class UniqueValidatorTest {

    EntityManager entityManager = Mockito.mock(EntityManager.class);
    UniqueValidator validator = new UniqueValidator(entityManager);

    @Test
    @DisplayName("Should not do validation when value is null or empty")
    void emptyText() {
        boolean nullValue = validator.isValid(null, null);
        Assertions.assertThat(nullValue).isTrue();
    }

    @Test
    @DisplayName("Should throws when result query returns more than 1 value")
    void manyItensOnUniqueValue() {
        ReflectionTestUtils.setField(validator, "klass", Object.class);
        ReflectionTestUtils.setField(validator, "fieldName", "uniqueField");

        Query query = Mockito.mock(Query.class);
        Mockito.doReturn(query).when(query).setParameter("value", "foo");
        Mockito.doReturn(List.of(new Object(), new Object())).when(query).getResultList();
        Mockito.doReturn(query).when(entityManager).createQuery(Mockito.anyString());

        Assertions.assertThatIllegalStateException()
                .isThrownBy(() -> validator.isValid("foo", null));
    }
}
