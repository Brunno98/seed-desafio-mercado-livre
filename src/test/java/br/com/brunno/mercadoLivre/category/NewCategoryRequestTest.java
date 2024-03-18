package br.com.brunno.mercadoLivre.category;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class NewCategoryRequestTest {

    EntityManager entityManager = Mockito.mock(EntityManager.class);

    @Test
    @DisplayName("when request has parent category id then should result in a category with parent")
    void parentCategory() {
        NewCategoryRequest request = new NewCategoryRequest();
        ReflectionTestUtils.setField(request, "name", "foo");
        ReflectionTestUtils.setField(request, "parentCategoryId", 1L);
        Category parentCategory = new Category("bar");
        doReturn(parentCategory).when(entityManager).find(Category.class, 1L);

        request.toDomain(entityManager);

        verify(entityManager).find(Category.class, 1L);
    }

    @Test
    @DisplayName("when request dont have parent category id then should result in a category without parent")
    void category() {
        NewCategoryRequest request = new NewCategoryRequest();
        ReflectionTestUtils.setField(request, "name", "foo");

        request.toDomain(entityManager);

        verify(entityManager, never()).find(Category.class, 1L);
    }
}
