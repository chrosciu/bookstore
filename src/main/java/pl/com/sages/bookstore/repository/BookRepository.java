package pl.com.sages.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.com.sages.bookstore.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByAuthor(String author);

    List<Book> findByRatingBetween(int from, int to);

    Optional<Book> findFirstByRating(int rating);

    int countAllByAuthor(String author);

    @Query("select max(b.price) from Book b")
    int findMaxPrice();

    @Query("from Book")
    List<Book> findAllByJPQL();

    @Query(value = "select * from books b where b.price >= :fromPrice", nativeQuery = true)
    List<Book> findByPriceHigherThan(int fromPrice);
}
