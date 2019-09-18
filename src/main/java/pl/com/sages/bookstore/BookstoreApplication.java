package pl.com.sages.bookstore;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.com.sages.bookstore.repository.BookRepository;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class BookstoreApplication {
	private final BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
}
