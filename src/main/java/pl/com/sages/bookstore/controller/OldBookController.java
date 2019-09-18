package pl.com.sages.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.model.Review;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.repository.ReviewRepository;

import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oldBooks")
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OldBookController {
    private BookRepository bookRepository;
    private ReviewRepository reviewRepository;

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

    @GetMapping("/booksWithReviews")
    @Transactional(readOnly = true)
    public void booksWithReviews() {
        var books = bookRepository.findAllWithReviews();
        log.info("Books: {}", books);
        var reviews = books.stream().flatMap(book -> book.getReviews().stream()).collect(Collectors.toList());
        log.info("Reviews: {}", reviews);
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

    @GetMapping("/{id}/reviews")
    public void getAllReviewsForBook(@PathVariable("id") int id) {
        log.info("{}", reviewRepository.findByBookId(id));
    }

    @PostMapping("/{id}/reviews")
    public void createReviewForBook(@PathVariable("id") int id) {
        var book = bookRepository.findById(id);
        book.ifPresentOrElse(b -> {
            var review = Review.builder()
                    .description(UUID.randomUUID().toString())
                    .rating(new Random().nextInt(50))
                    .book(b)
                    .build();
            var savedReview = reviewRepository.save(review);
            log.info("Saved review: {}", savedReview);
        }, () -> log.info("Book with id: {} does not exist", id));
    }
}
