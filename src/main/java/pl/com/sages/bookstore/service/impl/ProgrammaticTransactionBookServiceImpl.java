package pl.com.sages.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.metadata.TypeFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.dto.NewBookDto;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.repository.BookRepository;
import pl.com.sages.bookstore.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
public class ProgrammaticTransactionBookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final MapperFacade mapperFacade;
    private final TransactionTemplate transactionTemplate;

    @Override
    public List<BookDto> getAllBooks() {
        throw new NotYetImplementedException();
    }

    @Override
    public BookDto createBook(NewBookDto newBookDto) {
        throw new NotYetImplementedException();
    }

    @Override
    public BookDto updateBook(int id, NewBookDto newBookDto) {
        return transactionTemplate.execute(new TransactionCallback<BookDto>() {
            @Override
            public BookDto doInTransaction(TransactionStatus status) {
                var book = bookRepository.getOne(id);
                mapperFacade.map(newBookDto, book, null, TypeFactory.valueOf(Book.class));
                var savedBook = bookRepository.save(book);
                /*
                   Force rollback;
                   Without this automatic commit will take place at end of TransactionCallback
                 */
                status.setRollbackOnly();
                return mapperFacade.map(savedBook, BookDto.class);
            }
        });
    }

    @Override
    public void deleteBook(int id) {
        throw new NotYetImplementedException();
    }
}
