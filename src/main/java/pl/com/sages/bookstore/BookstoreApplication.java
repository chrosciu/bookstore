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
import pl.com.sages.bookstore.model.Review;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.repository.ReviewRepository;

import java.util.Collections;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
@Slf4j
public class BookstoreApplication {
	private final BookRepository bookRepository;
	private final ReviewRepository reviewRepository;

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

	@PostMapping("/booksWithException")
	@Transactional
	public void createBookWithException() {
		var book = Book.builder()
				.title(UUID.randomUUID().toString())
				.author(UUID.randomUUID().toString())
				.price(new Random().nextInt(100))
				.rating(new Random().nextInt(50))
				.build();
		var savedBook = bookRepository.save(book);
		log.info("{}", savedBook);
		throw new IllegalStateException("By design");
	}

	@PostMapping("/booksWithCheckedException")
	@Transactional
	public void createBookWithCheckedException() throws Exception {
		var book = Book.builder()
				.title(UUID.randomUUID().toString())
				.author(UUID.randomUUID().toString())
				.price(new Random().nextInt(100))
				.rating(new Random().nextInt(50))
				.build();
		var savedBook = bookRepository.save(book);
		log.info("{}", savedBook);
		throw new Exception("By design");
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

	@GetMapping("/books/{id}/reviews")
	@Transactional(readOnly = true)
	public void getAllReviewsForBook(@PathVariable("id") int id) {
		log.info("{}", bookRepository.findById(id).map(Book::getReviews).orElse(Collections.emptyList()));
		log.info("{}", reviewRepository.findByBookId(id));
	}

	@PostMapping("/books/{id}/reviews")
	@Transactional()
	public void createReviewForBook(@PathVariable("id") int id) {
		var book = bookRepository.getOne(id);
		var review = Review.builder()
				.description(UUID.randomUUID().toString())
				.rating(new Random().nextInt(50))
				.book(book)
				.build();
		var savedReview = reviewRepository.save(review);
		log.info("{}", savedReview);
	}

	@GetMapping("/query")
	@Transactional(readOnly = true)
	public void getByQuery() {
		log.info("{}", bookRepository.findByPriceLowerThanWithReviews(50));
		log.info("{}", bookRepository.findByPriceHigherThan(50));
		log.info("{}", bookRepository.findByAuthorNameStartingWith("M"));
		log.info("{}", bookRepository.findMaxPrice());
	}



}
