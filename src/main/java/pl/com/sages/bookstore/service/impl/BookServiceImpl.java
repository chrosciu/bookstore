package pl.com.sages.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.dto.NewBookDto;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.service.BookService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final MapperFacade mapperFacade;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(book -> mapperFacade.map(book, BookDto.class)).collect(toList());
    }

    @Override
    public BookDto createBook(NewBookDto newBook) {
        return mapperFacade.map(bookRepository.save(mapperFacade.map(newBook, Book.class)), BookDto.class);
    }

    @Override
    public BookDto updateBook(int id, NewBookDto newBook) {
        var book = bookRepository.getOne(id);
        mapperFacade.map(newBook, book, null, TypeFactory.valueOf(Book.class));
        return mapperFacade.map(bookRepository.save(book), BookDto.class);
    }

    @Override
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}
