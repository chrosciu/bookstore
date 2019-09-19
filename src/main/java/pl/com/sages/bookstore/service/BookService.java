package pl.com.sages.bookstore.service;

import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.dto.NewBookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto createBook(NewBookDto newBookDto);

    void updateBook(int id);

    void deleteBook(int id);

}
