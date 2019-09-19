package pl.com.sages.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.dto.NewBookDto;
import pl.com.sages.bookstore.service.BookService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks(Authentication authentication) {
        log.info("currentUser: {}", authentication.getPrincipal());
        return bookService.getAllBooks();
    }

    @PostMapping
    public BookDto createBook(@Valid @RequestBody NewBookDto newBookDto) {
        return bookService.createBook(newBookDto);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable("id") int id, @RequestBody NewBookDto newBookDto) {
        return bookService.updateBook(id, newBookDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
    }
}
