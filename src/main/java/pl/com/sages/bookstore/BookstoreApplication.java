package pl.com.sages.bookstore;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.repository.BookRepository;

import java.util.Random;
import java.util.UUID;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
@Slf4j
public class BookstoreApplication {
	private final BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@GetMapping("/books")
	@Transactional(readOnly = true)
	public void getAllBooks() {
		log.info("{}", bookRepository.findAll());
	}

	@PostMapping("/books")
	@Transactional
	public void createBook() {
		var book = Book.builder()
				.title(UUID.randomUUID().toString())
				.author(UUID.randomUUID().toString())
				.price(new Random().nextInt(100))
				.rating(new Random().nextInt(50))
				.build();
		var savedBook = bookRepository.save(book);
		log.info("{}", savedBook);
	}

	@PutMapping("/books/{id}")
	@Transactional
	public void updateBook(@PathVariable("id") int id) {
		bookRepository.findById(id).ifPresentOrElse(book -> {
			book.setPrice(book.getPrice() + 1);
			//bookRepository.save(book);
		}, () -> {
			log.info("No book with given id: {} found!", id);
		});
	}

	@DeleteMapping("/books/{id}")
	@Transactional
	public void deleteBook(@PathVariable("id") int id) {
		bookRepository.findById(id).ifPresentOrElse(bookRepository::delete, () -> {
			log.info("No book with given id: {} found!", id);
		});
	}



}
