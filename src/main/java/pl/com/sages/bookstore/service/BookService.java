package pl.com.sages.bookstore.service;

import pl.com.sages.bookstore.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    void createBook();

    void updateBook(int id);

    void deleteBook(int id);

}
