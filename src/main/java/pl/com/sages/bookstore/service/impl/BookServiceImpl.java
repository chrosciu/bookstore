package pl.com.sages.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.service.BookService;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        var books = bookRepository.findAll();
        var bookDtos = books.stream().map(this::map).collect(toList());
        return bookDtos;
    }

    @Override
    public void createBook() {
        var book = Book.builder()
                .title(UUID.randomUUID().toString())
                .author(UUID.randomUUID().toString())
                .price(new Random().nextInt(100))
                .rating(new Random().nextInt(50))
                .build();
        var savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
    }

    @Override
    public void updateBook(int id) {
        var optionalBook = bookRepository.findById(id);
        optionalBook.ifPresentOrElse(book -> {
            book.setPrice(book.getPrice() + 1);
            log.info("Updated book: {}", book);
            //WARNING: Book will be saven even without call to bookRepository.save !!!
            //var updatedBook = bookRepository.save(book);
            //log.info("Updated book: {}", updatedBook);
        }, () -> log.info("Book with id: {} does not exist", id));
    }

    @Override
    public void deleteBook(int id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            log.info("Book with id: {} deleted", id);
        } else {
            log.info("Book with id: {} does not exist", id);
        }
    }

    private BookDto map(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setTitle(book.getTitle());
        bookDto.setPrice(book.getPrice());
        bookDto.setRating(book.getRating());
        return bookDto;
    }
}
