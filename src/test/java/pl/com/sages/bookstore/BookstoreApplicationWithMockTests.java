package pl.com.sages.bookstore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.service.BookService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookstoreApplicationWithMockTests {
    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    private static final Book SOME_BOOK = Book.builder().title("Wladca pierscieni").build();

    @Test
    public void shouldReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(SOME_BOOK));
        List<BookDto> books = bookService.getAllBooks();
        assertThat(books).hasSize(1)
                .extracting("title").containsExactly("Wladca pierscieni");
    }

}
