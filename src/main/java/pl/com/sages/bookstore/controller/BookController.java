package pl.com.sages.bookstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.dto.BookWithReviewsDto;
import pl.com.sages.bookstore.dto.NewBookDto;
import pl.com.sages.bookstore.service.BookService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
@Slf4j
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public BookDto createBook(@RequestBody @Valid NewBookDto newBook) {
        return bookService.createBook(newBook);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable("id") int id, @RequestBody @Valid NewBookDto newBook) {
        return bookService.updateBook(id, newBook);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/withReviews")
    public List<BookWithReviewsDto> getAllBooksWithReviews() {
        return bookService.getAllBooksWithReviews();
    }


}
