package mate.academy.bookstore.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.BookDto;
import mate.academy.bookstore.dto.BookSearchParametersDto;
import mate.academy.bookstore.dto.CreateBookRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.BookMapper;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.book.BookRepository;
import mate.academy.bookstore.repository.book.BookSpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private static final String CANNOT_GET_BOOK_BY_ID_EXCEPTION = "Can't get book by id: ";
    private static final String CANNOT_UPDATE_BOOK_BY_ID_EXCEPTION
            = "Can't update book, because id not exist: ";
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(CANNOT_GET_BOOK_BY_ID_EXCEPTION + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto bookRequestDto) {
        if (bookRepository.existsById(id)) {
            Book updatedBook = bookMapper.toModel(bookRequestDto);
            updatedBook.setId(id);
            return bookMapper.toDto(bookRepository.save(updatedBook));
        }
        throw new EntityNotFoundException(CANNOT_UPDATE_BOOK_BY_ID_EXCEPTION + id);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookDto> search(BookSearchParametersDto searchParameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
