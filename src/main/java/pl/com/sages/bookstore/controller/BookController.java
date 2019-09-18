package pl.com.sages.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.repository.BookRepository;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@Slf4j
@RequiredArgsConstructor
public class BookController {
    private final BookRepository repository;

    @GetMapping
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

}
