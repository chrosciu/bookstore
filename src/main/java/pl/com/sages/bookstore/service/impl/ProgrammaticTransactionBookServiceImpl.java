package pl.com.sages.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.dto.BookWithReviewsDto;
import pl.com.sages.bookstore.dto.NewBookDto;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProgrammaticTransactionBookServiceImpl implements BookService {
    private final TransactionTemplate transactionTemplate;
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
    public BookDto updateBook(int id, NewBookDto newBook) {
        return transactionTemplate.execute(new TransactionCallback<BookDto>() {
            @Override
            public BookDto doInTransaction(TransactionStatus status) {
                var book = bookRepository.getOne(id);
                mapperFacade.map(newBook, book, null, TypeFactory.valueOf(Book.class));
                var bookDto = mapperFacade.map(bookRepository.save(book), BookDto.class);
                status.setRollbackOnly();
                return bookDto;
            }
        });
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
