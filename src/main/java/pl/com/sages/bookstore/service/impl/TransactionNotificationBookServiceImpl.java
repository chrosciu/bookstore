package pl.com.sages.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.dto.BookWithReviewsDto;
import pl.com.sages.bookstore.dto.NewBookDto;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
@Slf4j
public class TransactionNotificationBookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final MapperFacade mapperFacade;

    @Override
    public List<BookDto> getAllBooks() {
        throw new NotYetImplementedException();
    }

    @Override
    public BookDto createBook(NewBookDto newBook) {
        throw new NotYetImplementedException();
    }

    @Override
    @Transactional
    public BookDto updateBook(int id, NewBookDto newBook) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (TransactionSynchronization.STATUS_ROLLED_BACK == status) {
                    log.info("Rolled back!");
                }
            }
        });
        var book = bookRepository.getOne(id);
        mapperFacade.map(newBook, book, null, TypeFactory.valueOf(Book.class));
        var bookDto = mapperFacade.map(bookRepository.save(book), BookDto.class);
        throw new NullPointerException();
        //return bookDto;
    }

    @Override
    public void deleteBook(int id) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<BookWithReviewsDto> getAllBooksWithReviews() {
        throw new NotYetImplementedException();
    }

}
