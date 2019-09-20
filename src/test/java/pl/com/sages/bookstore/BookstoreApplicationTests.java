package pl.com.sages.bookstore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.sages.bookstore.service.BookService;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookstoreApplicationTests {
	@Autowired
	private BookService bookService;

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

}
