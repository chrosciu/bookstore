package pl.com.sages.bookstore;

import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.dto.NewBookDto;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.service.BookService;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookstoreApplicationTests {

	private static final String SOME_TITLE = "aaa";
	private static final String SOME_AUTHOR = "bbb";
	private static final NewBookDto SOME_NEW_BOOK_DTO = NewBookDto.builder()
			.title(SOME_TITLE).author(SOME_AUTHOR).build();

	@Autowired
	private BookService bookService;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private MapperFacade mapperFacade;

	@Test
	public void contextLoads() {
	}

	@Test
	public void shouldSetupBookService() {
		assertThat(bookService).isNotNull();
	}

	@Test
	public void shouldReturnAllBooks() {
		var books = bookService.getAllBooks();
		assertThat(books).hasSize(2)
				.extracting("title").containsExactly("Dziady", "Kordian");
	}

	@Test
	public void shouldCreateAndGetNewBook(){
		//when
		var bookDto = bookService.createBook(SOME_NEW_BOOK_DTO);

		//then
		var bookFromDatabase = bookRepository.getOne(bookDto.getId());
		var bookFromDatabaseDto = mapperFacade.map(bookFromDatabase, BookDto.class);
		assertThat(bookDto).isEqualTo(bookFromDatabaseDto)
				.extracting("author", "title")
				.containsExactly(SOME_AUTHOR, SOME_TITLE);
	}

}
