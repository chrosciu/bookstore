package pl.com.sages.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.dto.NewBookDto;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.service.BookService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
//@Primary
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final MapperFacade mapperFacade;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("currentUser: {}", authentication.getPrincipal());
        var books = bookRepository.findAll();
        var bookDtos = books.stream().map(book -> mapperFacade.map(book, BookDto.class)).collect(toList());
        return bookDtos;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto createBook(NewBookDto newBookDto) {
        var book = mapperFacade.map(newBookDto, Book.class);
        var savedBook = bookRepository.save(book);
        return mapperFacade.map(savedBook, BookDto.class);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto updateBook(int id, NewBookDto newBookDto) {
        var book = bookRepository.getOne(id);
        //mapperFacade.map(newBookDto, book); //does not work due to proxy around Book
        mapperFacade.map(newBookDto, book, null, TypeFactory.valueOf(Book.class));
        var savedBook = bookRepository.save(book);
        return mapperFacade.map(savedBook, BookDto.class);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    /*
    private BookDto map(Book book) {
        var bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setTitle(book.getTitle());
        bookDto.setPrice(book.getPrice());
        bookDto.setRating(book.getRating());
        return bookDto;
    }

    private Book mapNew(NewBookDto newBookDto) {
        var book = new Book();
        book.setAuthor(newBookDto.getAuthor());
        book.setTitle(newBookDto.getTitle());
        book.setPrice(newBookDto.getPrice());
        book.setRating(newBookDto.getRating());
        return book;
    }
     */
}
