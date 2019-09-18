package pl.com.sages.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.sages.bookstore.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
