package pl.com.sages.bookstore.service;

import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.mockito.Mockito;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.service.impl.BookServiceImpl;

public class BookServiceImplTest {

    @Test
    public void test() {
        MapperFacade mapperFacade = Mockito.mock(MapperFacade.class);
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        BookService bookService = new BookServiceImpl(bookRepository, mapperFacade);
        //Mockito.verify(bookRepository).deleteById(7);
        bookService.deleteBook(7);
        Mockito.verify(bookRepository).deleteById(7);
    }

}
