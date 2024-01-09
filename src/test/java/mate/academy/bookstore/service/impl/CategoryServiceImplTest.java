package mate.academy.bookstore.service.impl;

import java.util.Optional;
import mate.academy.bookstore.dto.category.CategoryResponseDto;
import mate.academy.bookstore.model.Category;
import mate.academy.bookstore.repository.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void getCategory_ByValidId_ReturnOk() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Technical");
        category.setDescription("Technical book");

        CategoryResponseDto categoryDto = new CategoryResponseDto(
                category.getId(), category.getName(), category.getDescription());

        Long id = 1L;

        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryResponseDto expected = categoryDto;
        CategoryResponseDto actual = categoryService.getById(id);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}