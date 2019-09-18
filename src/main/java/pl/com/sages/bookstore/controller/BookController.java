package pl.com.sages.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.repository.ReviewRepository;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BookController {
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public void getAllBooks() {
        log.info("getAllBooks: {}", bookRepository.findAll());
    }

    @GetMapping("/queries")
    @Transactional(readOnly = true)
    public void queries() {
        log.info("getAllBooksForAuthor: {}", bookRepository.findByAuthor("Mickiewicz"));
        log.info("countBooksForAuthor: {}", bookRepository.countAllByAuthor("Mickiewicz"));
        log.info("getAllBooksWithRatingBetween: {}", bookRepository.findByRatingBetween(0, 100));
        log.info("findByRating: {}", bookRepository.findFirstByRating(28).orElse(null));
        log.info("findMaxPrice: {}", bookRepository.findMaxPrice());
        log.info("findAllByJPQL: {}", bookRepository.findAllByJPQL());
        log.info("findByPriceHigherThan: {}", bookRepository.findByPriceHigherThan(20));
    }

    @PostMapping
    public void createBook() {
        var book = Book.builder()
                .title(UUID.randomUUID().toString())
                .author(UUID.randomUUID().toString())
                .price(new Random().nextInt(100))
                .rating(new Random().nextInt(50))
                .build();
        var savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
    }

    @PostMapping("/withUnchecked")
    public void createBookWithUncheckedException() {
        var book = Book.builder()
                .title(UUID.randomUUID().toString())
                .author(UUID.randomUUID().toString())
                .price(new Random().nextInt(100))
                .rating(new Random().nextInt(50))
                .build();
        var savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
        throw new IllegalStateException("Bo tak");
    }

    @PostMapping("/withChecked")
    @Transactional(rollbackFor = Exception.class)
    public void createBookWithCheckedException() throws Exception {
        var book = Book.builder()
                .title(UUID.randomUUID().toString())
                .author(UUID.randomUUID().toString())
                .price(new Random().nextInt(100))
                .rating(new Random().nextInt(50))
                .build();
        var savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
        throw new Exception("Bo tak");
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable("id") int id) {
        var optionalBook = bookRepository.findById(id);
        optionalBook.ifPresentOrElse(book -> {
            book.setPrice(book.getPrice() + 1);
            log.info("Updated book: {}", book);
            //WARNING: Book will be saven even without call to bookRepository.save !!!
            //var updatedBook = bookRepository.save(book);
            //log.info("Updated book: {}", updatedBook);
        }, () -> log.info("Book with id: {} does not exist", id));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") int id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            log.info("Book with id: {} deleted", id);
        } else {
            log.info("Book with id: {} does not exist", id);
        }
    }

    @GetMapping("/{id}/reviews")
    public void getAllReviewsForBook(@PathVariable("id") int id) {
        log.info("{}", reviewRepository.findByBookId(id));
    }

}
