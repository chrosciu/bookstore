package pl.com.sages.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.com.sages.bookstore.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByRatingBetween(int from, int to);

    @Query("from Book b left join fetch b.reviews where b.price <= :toPrice")
    List<Book> findByPriceLowerThanWithReviews(int toPrice);

    @Query(value = "select * from books where price >= :fromPrice", nativeQuery = true)
    List<Book> findByPriceHigherThan(int fromPrice);

    List<Book> findByAuthorNameStartingWith(String prefix);

    @Query("select max(b.price) from Book b")
    int findMaxPrice();
}
