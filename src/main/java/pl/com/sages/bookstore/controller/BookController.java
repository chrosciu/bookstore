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

import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

@RestController
@RequestMapping("/books")
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BookController {
    private final BookRepository repository;

    @GetMapping
    @Transactional(readOnly = true)
    public void getAllBooks() {
        log.info("getAllBooks: {}", repository.findAll());
    }

    @PostMapping
    public void createBook() {
        var book = Book.builder()
                .title(UUID.randomUUID().toString())
                .author(UUID.randomUUID().toString())
                .price(new Random().nextInt(100))
                .rating(new Random().nextInt(50))
                .build();
        var savedBook = repository.save(book);
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
        var savedBook = repository.save(book);
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
        var savedBook = repository.save(book);
        log.info("Saved book: {}", savedBook);
        throw new Exception("Bo tak");
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable("id") int id) {
        var optionalBook = repository.findById(id);
        optionalBook.ifPresentOrElse(book -> {
            book.setPrice(book.getPrice() + 1);
            var updatedBook = repository.save(book);
            log.info("Updated book: {}", updatedBook);
        }, () -> log.info("Book with id: {} does not exist", id));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Book with id: {} deleted", id);
        } else {
            log.info("Book with id: {} does not exist", id);
        }
    }

}
