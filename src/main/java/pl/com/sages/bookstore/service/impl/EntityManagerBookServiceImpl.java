package pl.com.sages.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.com.sages.bookstore.dto.BookDto;
import pl.com.sages.bookstore.dto.NewBookDto;
import pl.com.sages.bookstore.model.Book;
import pl.com.sages.bookstore.service.BookService;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@Primary
public class EntityManagerBookServiceImpl implements BookService {
    private final EntityManager entityManager;
    private final MapperFacade mapperFacade;

    @Override
    public List<BookDto> getAllBooks() {
        var jpqlQueryString = "from Book";
        var jpqlQuery = entityManager.createQuery(jpqlQueryString, Book.class);
        List<Book> books = jpqlQuery.getResultList();
        return books.stream().map(b -> mapperFacade.map(b, BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public BookDto createBook(NewBookDto newBookDto) {
        return null;
    }

    @Override
    public BookDto updateBook(int id, NewBookDto newBookDto) {
        return null;
    }

    @Override
    public void deleteBook(int id) {

    }
}
