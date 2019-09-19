package pl.com.sages.bookstore.service;

public interface BookService {

    void getAllBooks();

    void createBook();

    void updateBook(int id);

    void deleteBook(int id);

}
