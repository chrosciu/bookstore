package pl.com.sages.bookstore.service;

import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.dto.BookWithReviewsDto;
import pl.com.sages.bookstore.dto.NewBookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
    BookDto createBook(NewBookDto newBook);
    BookDto updateBook(int id, NewBookDto newBook);
    void deleteBook(int id);

    List<BookWithReviewsDto> getAllBooksWithReviews();
}
