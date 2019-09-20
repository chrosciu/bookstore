package pl.com.sages.bookstore.service;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.service.impl.BookServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Slf4j
public class BookServiceImplTest {

    private BookService bookService;
    private BookRepository bookRepository;
    private MapperFacade mapperFacade;

    private static final Book SOME_BOOK = Book.builder().id(1).build();
    private static final Book SOME_OTHER_BOOK = Book.builder().id(2).build();
    private static final BookDto SOME_BOOK_DTO = BookDto.builder().title("A").build();
    private static final BookDto SOME_OTHER_BOOK_DTO = BookDto.builder().title("B").build();

    @Before
    public void init() {
        bookRepository = Mockito.mock(BookRepository.class);
        mapperFacade = Mockito.mock(MapperFacade.class);
        bookService = new BookServiceImpl(bookRepository, mapperFacade);
    }

    @Test
    public void shouldReturnEmptyListIfThereAreNoBooksInRepo() {
        //given
        //not necessary as mock returns empty list by default

        //when
        var books = bookService.getAllBooks();

        //then
        assertThat(books).isEmpty();
    }

    @Test
    public void shouldReturnBooksFromRepo() {
        //given
        when(bookRepository.findAll()).thenReturn(List.of(SOME_BOOK, SOME_OTHER_BOOK));
        when(mapperFacade.map(SOME_BOOK, BookDto.class)).thenReturn(SOME_BOOK_DTO);
        when(mapperFacade.map(SOME_OTHER_BOOK, BookDto.class)).thenReturn(SOME_OTHER_BOOK_DTO);

        //when
        var books = bookService.getAllBooks();

        //then
        assertThat(books).hasSameElementsAs(List.of(SOME_BOOK_DTO, SOME_OTHER_BOOK_DTO));
    }



}
