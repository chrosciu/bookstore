package pl.com.sages.bookstore;

import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.service.BookService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookStoreMockApplicationTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    static MapperFacade mapperFacade2 = Mockito.mock(MapperFacade.class, "my nice mock");

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public MapperFacade mapperFacade() {
            return mapperFacade2;
        }
    }

    @Test
    public void test() {
        var book = Book.builder().build();
        when(bookRepository.findAll()).thenReturn(List.of(book));
        //verify(mapperFacade, times(1)).map(book, BookDto.class);
        verifyZeroInteractions(mapperFacade2);
        assertThat(bookService.getAllBooks()).hasSize(1);
    }

}
