package mate.academy.bookstore.repository.book;

import static org.junit.jupiter.api.Assertions.assertEquals;

import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Test
    @Sql(scripts = {
            "classpath:database/books/add-books.sql",
            "classpath:database/categories/add-categories.sql",
            "classpath:database/books_categories/add-books_categories.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books_categories/remove-books_categories.sql",
            "classpath:database/categories/remove-categories.sql",
            "classpath:database/books/remove-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllBooksByCategoryId_ReturnOk() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Technical");
        category.setName("Technical book");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean code");
        book.setAuthor("Robert Martin");
        book.setIsbn("9780307474278");
        book.setPrice(new BigDecimal("1111"));
        book.setDescription("description");
        book.setCategories(Set.of(category));

        List<Book> expected = List.of(book);

        List<Book> actual = bookRepository.findAllByCategoryId(category.getId());

        assertEquals(1, actual.size());
        EqualsBuilder.reflectionEquals(expected.get(0), actual.get(0), "id");
    }

    @Autowired
    private BookRepository bookRepository;

}