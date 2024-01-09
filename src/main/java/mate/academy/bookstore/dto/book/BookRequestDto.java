package mate.academy.bookstore.dto.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import mate.academy.bookstore.validation.Isbn;

@Data
@Accessors(chain = true)
public class BookRequestDto {
    @NotNull(message = "can't be null")
    @Size(min = 3, max = 30)
    private String title;
    @NotNull(message = "can't be null")
    @Size(min = 3, max = 30)
    private String author;
    @Isbn
    private String isbn;
    @Positive(message = "must be more than 0")
    @NotNull(message = "can't be null")
    private BigDecimal price;
    @NotEmpty(message = "can't be empty or null")
    private Set<Long> categoryIds;
    private String description;
    private String coverImage;
}
